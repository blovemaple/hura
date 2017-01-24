package com.github.blovemaple.hura.wordana.wordcomp;

import java.util.Arrays;
import java.util.List;

/**
 * 前缀。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum Prefix implements WordComponent {
	;

	@Override
	public String literal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String showLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String desc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate(List<WordComponent> previousComps) {
		// 前一部分不能是特殊词尾
		return !Arrays.asList(EndingSpecial.values()).contains(previousComps.get(previousComps.size() - 1));
	}

}
