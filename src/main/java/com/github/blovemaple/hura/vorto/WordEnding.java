package com.github.blovemaple.hura.vorto;

/**
 * 词类词尾。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum WordEnding {
	O("名词", "o"), A("形容词", "a"), E("副词", "e"), I("动词", "i"),

	;

	private String type;
	private String ending;

	private WordEnding(String type, String ending) {
		this.type = type;
		this.ending = ending;
	}

	public String getType() {
		return type;
	}

	public String getEnding() {
		return ending;
	}

	public static WordEnding parseEnding(String vorto) {
		if (vorto.length() <= 2)
			return null;
		for (WordEnding ending : values()) {
			if (vorto.endsWith(ending.ending)) {
				return ending;
			}
		}
		return null;
	}

}
