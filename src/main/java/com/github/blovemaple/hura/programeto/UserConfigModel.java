package com.github.blovemaple.hura.programeto;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.github.blovemaple.hura.dal.UserConfig;

public class UserConfigModel {
	private Long defVortlistoId;
	private Boolean showQueryHistory;
	private List<String> hideSectionKeys;

	private List<VortaroSection> vortaroSections;

	public UserConfigModel(UserConfig config, List<VortaroSection> allSections) {
		this.defVortlistoId = config.getDefVortlistoId();
		this.showQueryHistory = config.getShowQueryHistory();
		this.hideSectionKeys = config.getHideSectionKeys();
		vortaroSections = allSections.stream()
				.filter(section -> hideSectionKeys != null && hideSectionKeys.contains(section.getKey()))
				.collect(toList());
	}

	public UserConfig toConfig() {
		UserConfig config = new UserConfig();
		config.setDefVortlistoId(defVortlistoId);
		config.setShowQueryHistory(showQueryHistory);
		config.setHideSectionKeys(hideSectionKeys);
		return config;
	}

	public Long getDefVortlistoId() {
		return defVortlistoId;
	}

	public void setDefVortlistoId(Long defVortlistoId) {
		this.defVortlistoId = defVortlistoId;
	}

	public Boolean getShowQueryHistory() {
		return showQueryHistory;
	}

	public void setShowQueryHistory(Boolean showQueryHistory) {
		this.showQueryHistory = showQueryHistory;
	}

	public List<String> getHideSectionKeys() {
		return hideSectionKeys;
	}

	public void setHideSectionKeys(List<String> hideSectionKeys) {
		this.hideSectionKeys = hideSectionKeys;
	}

	public List<VortaroSection> getVortaroSections() {
		return vortaroSections;
	}

	public void setVortaroSections(List<VortaroSection> vortaroSections) {
		this.vortaroSections = vortaroSections;
	}

}
