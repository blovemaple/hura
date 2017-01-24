package com.github.blovemaple.hura.wordana.wordcomp;

import java.util.List;

/**
 * 特殊词尾。j/n。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum EndingSpecial implements WordComponent {
	PLURAL("j", "表示复数"), //
	ACCUSATIVE("n", "表示宾格"); // TODO

	private String literal;
	private String desc;

	private EndingSpecial(String literal, String desc) {
		this.literal = literal;
		this.desc = desc;
	}

	@Override
	public String literal() {
		return literal;
	}

	@Override
	public String showLiteral() {
		return "-"+literal;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public boolean validate(List<WordComponent> previousComps) {
		switch(this){
		case PLURAL:
			
			break;
		case ACCUSATIVE:
			
			break;
		}
	}

}
