/**
 * Copyright (c) blackbear, Inc All Rights Reserved.
 */
package com.github.blovemaple.hura.util;

public class MyUtils {

	public static boolean hasChinese(String str) {
		return str.chars().anyMatch(c -> isChinese((char) c));
	}

	public static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}

}