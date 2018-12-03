package com.github.blovemaple.hura.programeto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.blovemaple.hura.PrivateConf;

@RestController
@RequestMapping("/hura-programeto")
public class ProgrametoService {
	@Autowired
	private PrivateConf privateConf;

	private WXSnsService wxService = WXSnsService.create();

	private Map<String, LoginInfo> loginInfos = new HashMap<>();

	private static final List<VortaroUnit> VORTARO_UNITS = List.of();

	/**
	 * 发送code登录，返回登录key。
	 * 
	 * @param code
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@RequestMapping("/login")
	private LoginResponse login(@RequestParam("code") String code) throws IOException, InterruptedException {
		WXCode2SessionResponse res = wxService.jscode2session(privateConf.getWxProgrametoAppid(),
				privateConf.getWxProgrametoSecret(), code, "authorization_code");
		switch (res.getErrcode()) {
		case WXCode2SessionResponse.ERRCODE_SUCCESS:
			return LoginResponse.success(login(res), conf(res.getUnionid()));
		case WXCode2SessionResponse.ERRCODE_INVALID:
			return LoginResponse.failed();
		default:
			return LoginResponse.failed();
		}
	}

	private String login(LoginInfo loginInfo) {
		String loginKey = UUID.randomUUID().toString();
		loginInfos.put(loginKey, loginInfo);
		return loginKey;
	}

	private UserConf conf(String unionid) {
		UserConf conf = new UserConf();
		conf.setVortaroUnits(VORTARO_UNITS);
		return conf;
	}

	/**
	 * 查询一个词典的结果。
	 */
	@RequestMapping("/query")
	private void query(@RequestParam("loginkey") String loginKey, @RequestParam("vorto") String vorto,
			@RequestParam("vortaro") String vortaro) {

	}
}
