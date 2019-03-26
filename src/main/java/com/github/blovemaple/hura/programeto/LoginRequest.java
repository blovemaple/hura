package com.github.blovemaple.hura.programeto;

import javax.validation.constraints.NotNull;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class LoginRequest {
	@NotNull
	private String loginCode;
	@NotNull
	private WxUserInfo userInfo;

	private Integer huraVersion = 10100;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public WxUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(WxUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getHuraVersion() {
		return huraVersion;
	}

	public void setHuraVersion(Integer huraVersion) {
		this.huraVersion = huraVersion;
	}

}
