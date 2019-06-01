package com.github.blovemaple.hura.programeto;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ValidateLoginResponse {
	private Boolean success;
	private UserConfigModel conf;

	public static ValidateLoginResponse success(UserConfigModel conf) {
		ValidateLoginResponse res = new ValidateLoginResponse();
		res.setSuccess(true);
		res.setConf(conf);
		return res;
	}

	public static ValidateLoginResponse failed() {
		ValidateLoginResponse res = new ValidateLoginResponse();
		res.setSuccess(false);
		return res;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public UserConfigModel getConf() {
		return conf;
	}

	public void setConf(UserConfigModel conf) {
		this.conf = conf;
	}

}
