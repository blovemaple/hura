package com.github.blovemaple.hura;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;

/**
 * 整合的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Vortaro {
	private VortaroSource chenVortaro = new ChenVortaro();
	private VortaroSource googleTrans = new GoogleTranslate();

	public List<VortaroResult> query(String vorto) {
		VortaroResult result = new VortaroResult();

		vorto = formatWord(vorto);

		try {
			List<VortaroSourceResult> sourceResult = chenVortaro.query(vorto);
			if (sourceResult != null && !sourceResult.isEmpty()) {
				result.setSource(chenVortaro);
				result.setResults(sourceResult);
				return Collections.singletonList(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			List<VortaroSourceResult> sourceResult = googleTrans.query(vorto);
			if (sourceResult != null && !sourceResult.isEmpty()) {
				result.setSource(googleTrans);
				result.setResults(sourceResult);
				return Collections.singletonList(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		REPLACE_LETTERS.put("uh", "ŭ");
		REPLACE_LETTERS.put("au", "aŭ");
		REPLACE_LETTERS.put("eu", "eŭ");
	}

	private static String formatWord(String vorto) {
		String word = vorto.trim().toLowerCase();
		for (Map.Entry<String, String> replace : REPLACE_LETTERS.entrySet())
			word = word.replaceAll(replace.getKey(), replace.getValue());
		return word;
	}

}
