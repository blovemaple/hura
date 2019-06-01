package com.github.blovemaple.hura.programeto;

import java.util.List;

import com.github.blovemaple.hura.vortlisto.VortlistoVortoModel;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GetVortlistoVortojResponse {
	private List<VortlistoVortoModel> vortlistoj;

	public GetVortlistoVortojResponse(List<VortlistoVortoModel> vortlistoj) {
		this.vortlistoj = vortlistoj;
	}

	public List<VortlistoVortoModel> getVortlistoj() {
		return vortlistoj;
	}

	public void setVortlistoj(List<VortlistoVortoModel> vortlistoj) {
		this.vortlistoj = vortlistoj;
	}

}
