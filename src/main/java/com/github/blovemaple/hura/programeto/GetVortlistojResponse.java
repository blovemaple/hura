package com.github.blovemaple.hura.programeto;

import java.util.List;

import com.github.blovemaple.hura.vortlisto.VortlistoModel;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GetVortlistojResponse {
	private List<VortlistoModel> vortlistoj;

	public GetVortlistojResponse(List<VortlistoModel> vortlistoj) {
		this.vortlistoj = vortlistoj;
	}

	public List<VortlistoModel> getVortlistoj() {
		return vortlistoj;
	}

	public void setVortlistoj(List<VortlistoModel> vortlistoj) {
		this.vortlistoj = vortlistoj;
	}

}
