package com.github.blovemaple.hura.programeto;

public class VortaroSection {
	private String key;
	private String name;
	private boolean canCopy;
	private boolean canLookup;

	public VortaroSection(String key, String name, boolean canCopy, boolean canLookup) {
		this.key = key;
		this.name = name;
		this.canCopy = canCopy;
		this.canLookup = canLookup;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanCopy() {
		return canCopy;
	}

	public void setCanCopy(boolean canCopy) {
		this.canCopy = canCopy;
	}

	public boolean isCanLookup() {
		return canLookup;
	}

	public void setCanLookup(boolean canLookup) {
		this.canLookup = canLookup;
	}

}
