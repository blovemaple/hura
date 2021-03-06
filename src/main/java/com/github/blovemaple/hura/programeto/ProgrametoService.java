package com.github.blovemaple.hura.programeto;

import static com.github.blovemaple.hura.source.VortaroSourceType.TRADUKILO;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blovemaple.hura.dal.ProgrametoLoginLog;
import com.github.blovemaple.hura.dal.ProgrametoLoginLogMapper;
import com.github.blovemaple.hura.dal.ProgrametoQueryLog;
import com.github.blovemaple.hura.dal.ProgrametoQueryLogMapper;
import com.github.blovemaple.hura.dal.User;
import com.github.blovemaple.hura.dal.UserDynamicSqlSupport;
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
			LoginResponse response = LoginResponse.success(login(res),
					conf(res.getUnionid(), request.getHuraVersion()));

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

	private UserConf conf(String unionid, int huraVersion) {
		UserConf conf = new UserConf();
		if (huraVersion >= 10200)
			conf.setVortaroSections(VORTARO_SECTIONS);
		else
			conf.setVortaroSections(
					VORTARO_SECTIONS.stream().filter(section -> !section.getKey().equals("piv")).collect(toList()));
		return conf;
	}

	private void saveUser(WxUserInfo userInfo, LoginInfo loginInfo) {
		Optional<User> userResult = userMapper
				.selectOne(c -> c.where(UserDynamicSqlSupport.openid, SqlBuilder.isEqualTo(loginInfo.getOpenid())));

		if (userResult.isEmpty()) {
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
			User user = userResult.get();
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
			return ValidateLoginResponse.success(conf(loginInfo.getUnionid(), huraVersion));
		} catch (InvalidLoginKeyException e) {
			return ValidateLoginResponse.failed();
		}
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

	private LoginInfo validateLogin0(String loginKey) throws InvalidLoginKeyException {
		if (loginKey == null)
			throw new InvalidLoginKeyException();
		LoginInfo loginInfo = loginInfos.get(loginKey);
		if (loginInfo == null)
			throw new InvalidLoginKeyException();
		return loginInfo;
	}

	private VortaroSource validateSectionKey(String sectionKey) throws InvalidSectionKeyException {
		if (sectionKey == null)
			throw new InvalidSectionKeyException();
		VortaroSource source = SOURCES_BY_KEY.get(sectionKey);
		if (source == null)
			throw new InvalidSectionKeyException();
		return source;
	}
}
