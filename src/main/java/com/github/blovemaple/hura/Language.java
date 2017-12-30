package com.github.blovemaple.hura;

import static com.github.blovemaple.hura.util.MyUtils.*;

/**
 * 语言。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum Language {
	CHINESE, ESPERANTO;

	public static Language determine(String str) {
		int zhCount = 0, eoCount = 0;
		for (char c : str.toCharArray()) {
			if (isChinese(c))
				zhCount++;
			if (isEsperanto(c))
				eoCount++;
		}
		return zhCount > eoCount ? CHINESE : ESPERANTO;
	}
}
