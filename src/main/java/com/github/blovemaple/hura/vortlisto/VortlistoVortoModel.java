package com.github.blovemaple.hura.vortlisto;

import javax.validation.constraints.NotNull;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class VortlistoVortoModel {
	private Long id;
	@NotNull
	private Long vortlistoId;
	@NotNull
	private String vorto;
	@NotNull
	private String signifo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVortlistoId() {
		return vortlistoId;
	}

	public void setVortlistoId(Long vortlistoId) {
		this.vortlistoId = vortlistoId;
	}

	public String getVorto() {
		return vorto;
	}

	public void setVorto(String vorto) {
		this.vorto = vorto;
	}

	public String getSignifo() {
		return signifo;
	}

	public void setSignifo(String signifo) {
		this.signifo = signifo;
	}

}
