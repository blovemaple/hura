/**
 * Copyright (c) blackbear, Inc All Rights Reserved.
 */
package com.github.blovemaple.hura.util;
import static java.util.stream.Collectors.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 一些工具。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class MyUtils {

	/**
	 * 判断指定字符串是否含有汉字。
	 */
	public static boolean hasChinese(String str) {
		return str.chars().anyMatch(c -> isChinese((char) c));
	}

	/**
	 * 判断指定字符是否是汉字。参考自：<br>
	 * https://github.com/mabe02/lanterna/blob/62b0b57cd62cc5cf9cd4ff7035c067ba1356598d/src/main/java/com/googlecode/lanterna/TerminalTextUtils.java
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(c);
		return (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B)
				|| (unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (unicodeBlock == Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
	}

	private static final Set<Integer> ESPERANTO_CHARS = "abcĉdefgĝhĥijĵklmnopqrsŝtuŭvwxyz-".chars().boxed()
			.collect(toSet());

	/**
	 * 判断指定字符是否是世界语。
	 */
	public static boolean isEsperanto(char c) {
		return ESPERANTO_CHARS.contains((int) c);
	}

	private static final Map<String, String> REPLACE_LETTERS = new HashMap<>();
	static {
		REPLACE_LETTERS.put("cx", "ĉ");
		REPLACE_LETTERS.put("gx", "ĝ");
		REPLACE_LETTERS.put("hx", "ĥ");
		REPLACE_LETTERS.put("jx", "ĵ");
		REPLACE_LETTERS.put("sx", "ŝ");
		REPLACE_LETTERS.put("ux", "ŭ");
		REPLACE_LETTERS.put("ch", "ĉ");
		REPLACE_LETTERS.put("gh", "ĝ");
		REPLACE_LETTERS.put("hh", "ĥ");
		REPLACE_LETTERS.put("jh", "ĵ");
		REPLACE_LETTERS.put("sh", "ŝ");
		REPLACE_LETTERS.put("au", "aŭ");
		REPLACE_LETTERS.put("eu", "eŭ");
	}

	/**
	 * trim、小写、帽子字母还原。
	 */
	public static String formatWord(String vorto) {
		String word = vorto.trim().toLowerCase();
		for (Map.Entry<String, String> replace : REPLACE_LETTERS.entrySet())
			word = word.replaceAll(replace.getKey(), replace.getValue());
		return word;
	}
}