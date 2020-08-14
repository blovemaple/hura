package com.github.blovemaple.hura.source;

import java.util.List;

/**
 * PIV词典查询结果。
 * 
 * @author chentong02
 */
public class PivResponse {
	private String pivdok;
	private List<List<Integer>> vortoj;

	public String getPivdok() {
		return pivdok;
	}

	public void setPivdok(String pivdok) {
		this.pivdok = pivdok;
	}

	public List<List<Integer>> getVortoj() {
		return vortoj;
	}

	public void setVortoj(List<List<Integer>> vortoj) {
		this.vortoj = vortoj;
	}

}
