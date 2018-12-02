package com.github.blovemaple.hura.programeto;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.blovemaple.hura.PrivateConf;

@RestController
@RequestMapping("/hura-programeto")
public class ProgrametoService {
	@Autowired
	private PrivateConf privateConf;

	private WXSnsService wxService = WXSnsService.create();

	/**
	 * 发送code登录，返回登录key。
	 * 
	 * @param code
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@RequestMapping("/login")
	private LoginResponse login(String code) throws IOException, InterruptedException {
		WXCode2SessionResponse res = wxService.jscode2session(privateConf.getWxProgrametoAppid(),
				privateConf.getWxProgrametoSecret(), code, "authorization_code");
		switch (res.getErrcode()) {
		case WXCode2SessionResponse.ERRCODE_SUCCESS:
			return LoginResponse.success(login(res));
		case WXCode2SessionResponse.ERRCODE_INVALID:
			return LoginResponse.failed();
		default:
			return LoginResponse.failed();
		}
	}

	private String login(LoginInfo loginInfo) {
		// TODO
		return null;
	}
}
