package com.github.blovemaple.hura.programeto;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ValidateLoginResponse {
	private Boolean success;

	public static ValidateLoginResponse success() {
		ValidateLoginResponse res = new ValidateLoginResponse();
		res.setSuccess(true);
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

}
