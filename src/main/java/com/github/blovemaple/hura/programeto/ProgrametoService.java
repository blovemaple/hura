package com.github.blovemaple.hura.programeto;

import static com.github.blovemaple.hura.source.VortaroSourceType.TRADUKILO;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blovemaple.hura.dal.ProgrametoLoginLog;
import com.github.blovemaple.hura.dal.ProgrametoLoginLogMapper;
import com.github.blovemaple.hura.dal.ProgrametoQueryLog;
import com.github.blovemaple.hura.dal.ProgrametoQueryLogMapper;
import com.github.blovemaple.hura.dal.User;
import com.github.blovemaple.hura.dal.UserConfig;
import com.github.blovemaple.hura.dal.UserConfigMapper;
import com.github.blovemaple.hura.dal.UserExample;
import com.github.blovemaple.hura.dal.UserMapper;
import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate2;
import com.github.blovemaple.hura.source.LernuVortaro;
import com.github.blovemaple.hura.source.Piv;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.source.Wiktionary;
import com.github.blovemaple.hura.util.MyUtils;
import com.github.blovemaple.hura.util.PrivateConf;
import com.github.blovemaple.hura.vortlisto.VortlistoModel;
import com.github.blovemaple.hura.vortlisto.VortlistoService;
import com.github.blovemaple.hura.vortlisto.VortlistoVortoModel;
import com.github.blovemaple.hura.vorto.Lemmatization;

import retrofit2.Response;

/**
 * Hura微信小程序服务。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@RestController
@RequestMapping("/hura-programeto")
public class ProgrametoService {
	private static final Logger logger = LoggerFactory.getLogger(ProgrametoService.class);

	@Autowired
	private PrivateConf privateConf;
	@Autowired
	private ProgrametoLoginLogMapper loginLogMapper;
	@Autowired
	private ProgrametoQueryLogMapper queryLogMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserConfigMapper userConfigMapper;
	@Autowired
	private VortlistoService vortlistoService;

	private WXSnsService wxService = WXSnsService.create();
	private ObjectMapper jackson = new ObjectMapper();

	private Map<String, LoginInfo> loginInfos = new HashMap<>();
	{
		loginInfos.put("0", new LoginInfo());
	}

	private static final List<VortaroSource> SOURCES = List.of( //
			new Lemmatization(), //
			new ChenVortaro(), new LernuVortaro(), //
			new Wiktionary(), //
			new Piv(), //
			new GoogleTranslate2() //
	);
	private static final Map<String, VortaroSource> SOURCES_BY_KEY = //
			SOURCES.stream().collect(
					Collectors.toMap(source -> source.getClass().getSimpleName().toLowerCase(), source -> source));
	private static final List<VortaroSection> VORTARO_SECTIONS = //
			SOURCES.stream().map(source -> //
			new VortaroSection( //
					source.getClass().getSimpleName().toLowerCase(), //
					source.name(), //
					true, //
					source.type() == TRADUKILO, //
					source.hasDetail() //
			)).collect(Collectors.toList());

	/**
	 * 发送code登录，返回登录key。
	 * 
	 * @param code
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Transactional
	public LoginResponse login(@RequestBody LoginRequest request) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		Response<WXCode2SessionResponse> httpRes = wxService.jscode2session(privateConf.getWxProgrametoAppid(),
				privateConf.getWxProgrametoSecret(), request.getLoginCode(), "authorization_code").execute();
		if (!httpRes.isSuccessful()) {
			logger.error("Requesting wxService.jscode2session failed: " + httpRes);
			return LoginResponse.failed();
		}

		WXCode2SessionResponse res = httpRes.body();
		logger.info("Requesting wxService.jscode2session succeeded: " + httpRes + ", " + res);
		if (res.getErrcode() == null)
			res.setErrcode(WXCode2SessionResponse.ERRCODE_SUCCESS);
		switch (res.getErrcode()) {
		case WXCode2SessionResponse.ERRCODE_SUCCESS:
			LoginResponse response = LoginResponse.success(login(res), conf(res));

			saveUser(request.getUserInfo(), res);

			ProgrametoLoginLog log = new ProgrametoLoginLog();
			log.setTime(new Date());
			log.setCost((int) (System.currentTimeMillis() - startTime));
			log.setCode(request.getLoginCode());
			log.setOpenid(res.getOpenid());
			log.setUnionid(res.getUnionid());
			loginLogMapper.insertSelective(log);

			return response;
		case WXCode2SessionResponse.ERRCODE_INVALID:
			return LoginResponse.failed();
		default:
			logger.error("Requesting wxService.jscode2session failed with errcode: " + httpRes);
			return LoginResponse.failed();
		}
	}

	private String login(LoginInfo loginInfo) {
		String loginKey = UUID.randomUUID().toString();
		loginInfos.put(loginKey, loginInfo);
		return loginKey;
	}

	private UserConfigModel conf(LoginInfo loginInfo) {
		UserConfig config = userConfigMapper.selectByPrimaryKey(loginInfo.getOpenid());
		if (config == null) {
			config = initConfig(loginInfo.getOpenid());
		}

		UserConfigModel conf = new UserConfigModel(config,  VORTARO_SECTIONS);

		if (conf.getDefVortlistoId() == null) {
			conf.setDefVortlistoId(getDefaultVortlistoId(loginInfo));
		}

		return conf;
	}

	private UserConfig initConfig(String openid) {
		UserConfig config = new UserConfig();
		config.setOpenid(openid);
		config.setDefVortlistoId(null);
		config.setShowQueryHistory(true);
		config.setHideSectionKeys(List.of());
		config.setModtime(new Date());

		userConfigMapper.insert(config);
		return config;
	}

	private void saveUser(WxUserInfo userInfo, LoginInfo loginInfo) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andOpenidEqualTo(loginInfo.getOpenid());
		List<User> users = userMapper.selectByExample(userExample);

		if (users.isEmpty()) {
			User user = new User();
			user.setOpenid(loginInfo.getOpenid());
			user.setUnionid(loginInfo.getUnionid());
			user.setAddtime(new Date());
			BeanUtils.copyProperties(userInfo, user);
			try {
				logger.info("New user: " + jackson.writeValueAsString(userInfo));
				logger.info("DB user: " + jackson.writeValueAsString(user));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			userMapper.insertSelective(user);
		} else {
			User user = users.get(0);
			BeanUtils.copyProperties(userInfo, user);
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	/**
	 * 验证登录key是否有效。
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/validatelogin")
	public ValidateLoginResponse validateLogin(@RequestParam("loginkey") String loginKey,
			@RequestParam(value = "huraversion", defaultValue = "10100") int huraVersion) {
		try {
			LoginInfo loginInfo = validateLogin0(loginKey);
			return ValidateLoginResponse.success(conf(loginInfo));
		} catch (InvalidLoginKeyException e) {
			return ValidateLoginResponse.failed();
		}
	}

	@RequestMapping(value = "/conf", method = RequestMethod.PATCH)
	@Transactional
	public UserConfigModel modConfig(@RequestParam("loginkey") String loginKey,
			@RequestBody UserConfigModel configModel) throws InvalidLoginKeyException {
		LoginInfo loginInfo = validateLogin0(loginKey);

		UserConfig config = configModel.toConfig();
		config.setOpenid(loginInfo.getOpenid());
		config.setModtime(new Date());
		userConfigMapper.updateByPrimaryKeySelective(config);

		return conf(loginInfo);
	}

	/**
	 * 查询一个词典的结果。
	 * 
	 * @param loginKey
	 * @param query
	 * @param sectionKey
	 * @param isDetail
	 * 
	 * @throws InvalidLoginKeyException
	 * @throws InvalidSectionKeyException
	 * @throws IOException
	 */
	@RequestMapping("/query")
	public QueryResponse query(@RequestParam("loginkey") String loginKey, @RequestParam("query") String query,
			@RequestParam("sectionkey") String sectionKey,
			@RequestParam(value = "isdetail", defaultValue = "false") boolean isDetail)
			throws InvalidLoginKeyException, InvalidSectionKeyException, IOException {
		long startTime = System.currentTimeMillis();

		LoginInfo loginInfo = validateLogin0(loginKey);
		VortaroSource source = validateSectionKey(sectionKey);

		if (query == null || query.isBlank())
			return QueryResponse.empty();

		// 格式化
		String formatQuery = MyUtils.formatWord(query);
		String baseForm = formatQuery;
		if (source.getClass() != Lemmatization.class) {
			// 查询的source不是单词解析，取单词原型
			baseForm = Lemmatization.parse(formatQuery).getBaseForm();
		}

		// 先查query，query没查到再查原型
		List<VortaroSourceResult> sourceResults = source.query(formatQuery, isDetail);
		if (sourceResults == null || sourceResults.isEmpty())
			if (!baseForm.equals(formatQuery))
				sourceResults = source.query(baseForm, isDetail);

		if (sourceResults == null)
			sourceResults = List.of();

		ProgrametoQueryLog log = new ProgrametoQueryLog();
		log.setTime(new Date());
		log.setCost((int) (System.currentTimeMillis() - startTime));
		log.setOpenid(loginInfo.getOpenid());
		log.setUnionid(loginInfo.getUnionid());
		log.setQuery(query);
		log.setSectionKey(sectionKey);
		log.setIsDetail(isDetail);
		log.setHasResult(!sourceResults.isEmpty());
		// 查询详细内容不记录result，PIV的详细内容html太长了（可能有几百K）
		log.setResult(isDetail ? "" : jackson.writeValueAsString(sourceResults));
		queryLogMapper.insertSelective(log);

		return new QueryResponse(sourceResults);
	}

	private VortaroSource validateSectionKey(String sectionKey) throws InvalidSectionKeyException {
		if (sectionKey == null)
			throw new InvalidSectionKeyException();
		VortaroSource source = SOURCES_BY_KEY.get(sectionKey);
		if (source == null)
			throw new InvalidSectionKeyException();
		return source;
	}

	@RequestMapping(value = "/vortlistoj", method = RequestMethod.GET)
	public GetVortlistojResponse getVortlistoj(@RequestParam("loginkey") String loginKey)
			throws InvalidLoginKeyException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		List<VortlistoModel> vortlistoj = vortlistoService.getVortlistojByUser(loginInfo);
		return new GetVortlistojResponse(vortlistoj);
	}

	@RequestMapping(value = "/vortlistoj", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public VortlistoModel addVortlisto(@RequestParam("loginkey") String loginKey, @RequestParam("name") String name)
			throws InvalidLoginKeyException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		VortlistoModel listo = new VortlistoModel(name);
		vortlistoService.addVortlisto(loginInfo, listo);
		return listo;
	}

	@RequestMapping(value = "/vortlistoj/{id}", method = RequestMethod.PUT)
	@Transactional
	public VortlistoModel modVortlisto(@RequestParam("loginkey") String loginKey, @PathVariable Long id,
			@RequestBody VortlistoModel vortlisto) throws InvalidLoginKeyException, VortlistoNotExistException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		validateVortlisto(loginInfo, id);

		vortlisto.setId(id);
		VortlistoModel modifiedOne = vortlistoService.modVortlisto(loginInfo, vortlisto);
		return modifiedOne;
	}

	@RequestMapping(value = "/vortlistoj/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void delVortlisto(@RequestParam("loginkey") String loginKey, @PathVariable Long id)
			throws InvalidLoginKeyException, VortlistoNotExistException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		validateVortlisto(loginInfo, id);

		vortlistoService.delVortlisto(loginInfo, id);
	}

	@RequestMapping(value = "/vortlistoj/{vortlistoId}/vortoj", method = RequestMethod.GET)
	public GetVortlistoVortojResponse getVortlistoVortoj(@RequestParam("loginkey") String loginKey,
			@PathVariable Long vortlistoId) throws InvalidLoginKeyException, VortlistoNotExistException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		validateVortlisto(loginInfo, vortlistoId);

		List<VortlistoVortoModel> vortoj = vortlistoService.getVortlistoVortojByListoId(loginInfo, vortlistoId);
		return new GetVortlistoVortojResponse(vortoj);
	}

	@RequestMapping(value = "/vortlistoj/{vortlistoId}/vortoj/{vorto}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<VortlistoVortoModel> addVortlistVorto(@RequestParam("loginkey") String loginKey,
			@PathVariable Long vortlistoId, @PathVariable String vorto, @RequestBody VortlistoVortoModel vortoModel)
			throws InvalidLoginKeyException, VortlistoNotExistException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		if (vortlistoId == 0L) {
			Long defVortlistoId = getDefaultVortlistoId(loginInfo);
			if (defVortlistoId == null) {
				throw new VortlistoNotExistException();
			}
			vortlistoId = defVortlistoId;
		}
		validateVortlisto(loginInfo, vortlistoId);
		vortoModel.setVorto(vorto);

		VortlistoVortoModel existed = vortlistoService.getVortlistoVorto(loginInfo, vortlistoId, vorto);
		if (existed != null) {
			VortlistoVortoModel modified = vortlistoService.modVortlistoVorto(loginInfo, vortoModel);
			return new ResponseEntity<VortlistoVortoModel>(modified, HttpStatus.OK);
		} else {
			VortlistoVortoModel created = vortlistoService.addVortlistoVorto(loginInfo, vortoModel);
			return new ResponseEntity<VortlistoVortoModel>(created, HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/vortlistoj/{vortlistoId}/vortoj/{vorto}", method = RequestMethod.DELETE)
	@Transactional
	public void delVortlistVorto(@RequestParam("loginkey") String loginKey, @PathVariable Long vortlistoId,
			@PathVariable String vorto)
			throws InvalidLoginKeyException, VortlistoNotExistException, VortlistoVortoNotExistException {
		LoginInfo loginInfo = validateLogin0(loginKey);
		validateVortlisto(loginInfo, vortlistoId);

		VortlistoVortoModel vortoModel = vortlistoService.getVortlistoVorto(loginInfo, vortlistoId, vorto);
		if (vortoModel != null) {
			vortlistoService.delVortlistoVorto(loginInfo, List.of(vortoModel.getId()));
		} else {
			throw new VortlistoVortoNotExistException();
		}
	}

	private VortlistoModel validateVortlisto(LoginInfo loginInfo, Long id) throws VortlistoNotExistException {
		VortlistoModel existOne = vortlistoService.getVortlisto(loginInfo, id);
		if (existOne == null)
			throw new VortlistoNotExistException();
		return existOne;
	}

	private Long getDefaultVortlistoId(LoginInfo loginInfo) {
		UserConfig config = userConfigMapper.selectByPrimaryKey(loginInfo.getOpenid());
		if (config != null && config.getDefVortlistoId() != null)
			return config.getDefVortlistoId();

		List<VortlistoModel> listoj = vortlistoService.getVortlistojByUser(loginInfo);
		if (!listoj.isEmpty())
			return listoj.get(listoj.size() - 1).getId();

		return null;
	}

	private LoginInfo validateLogin0(String loginKey) throws InvalidLoginKeyException {
		if (loginKey == null)
			throw new InvalidLoginKeyException();
		LoginInfo loginInfo = loginInfos.get(loginKey);
		if (loginInfo == null)
			throw new InvalidLoginKeyException();
		return loginInfo;
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
