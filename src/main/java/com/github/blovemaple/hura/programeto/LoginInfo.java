package com.github.blovemaple.hura.programeto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
	private String openid;
	@JsonProperty("session_key")
	private String sessionKey;
	private String unionid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
