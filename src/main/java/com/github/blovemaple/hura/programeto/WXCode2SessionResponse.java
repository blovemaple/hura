package com.github.blovemaple.hura.programeto;

public class WXCode2SessionResponse extends LoginInfo {
	private Integer errcode;
	private String errmsg;

	public static final int ERRCODE_SUCCESS = 0;
	public static final int ERRCODE_INVALID = 40029;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
