package com.github.blovemaple.hura.programeto;

public class LoginResponse {
	private Boolean success;
	private String loginKey;

	public static LoginResponse success(String loginKey) {
		LoginResponse res = new LoginResponse();
		res.setSuccess(true);
		res.setLoginKey(loginKey);
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

}
