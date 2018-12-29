package com.github.blovemaple.hura.programeto;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class LoginResponse {
	private Boolean success;
	private String loginKey;
	private UserConf conf;

	public static LoginResponse success(String loginKey, UserConf conf) {
		LoginResponse res = new LoginResponse();
		res.setSuccess(true);
		res.setLoginKey(loginKey);
		res.setConf(conf);
		return res;
	}

	public static LoginResponse failed() {
		LoginResponse res = new LoginResponse();
		res.setSuccess(false);
		return res;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public UserConf getConf() {
		return conf;
	}

	public void setConf(UserConf conf) {
		this.conf = conf;
	}

}
