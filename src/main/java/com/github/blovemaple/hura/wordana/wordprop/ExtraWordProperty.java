package com.github.blovemaple.hura.wordana.wordprop;

/**
 * 单词的属性，如宾格、复数。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum ExtraWordProperty {
	DESTINATIVE("目的格"), //
	PLURAL("复数");

	private String showName;

	private ExtraWordProperty(String showName) {
		this.showName = showName;
	}

	public String showName() {
		return showName;
	}
}
