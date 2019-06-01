package com.github.blovemaple.hura.vortlisto;

import javax.validation.constraints.NotNull;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class VortlistoModel {
	@NotNull
	private Long id;
	@NotNull
	private String name;

	public VortlistoModel(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
