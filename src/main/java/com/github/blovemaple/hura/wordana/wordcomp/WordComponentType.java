package com.github.blovemaple.hura.wordana.wordcomp;

/**
 * 单词组成部分的类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum WordComponentType {
	ROOT_REGULAR(null), //
	PREFIX("前缀"), //
	SUFFIX("后缀"), //
	PARTICLE("小品词"), //
	ENDING("词尾");

	private String desc;

	private WordComponentType(String desc) {
		this.desc = desc;
	}

	public String desc() {
		return desc;
	}
}
