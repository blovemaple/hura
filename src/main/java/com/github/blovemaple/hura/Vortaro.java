package com.github.blovemaple.hura;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate;
import com.github.blovemaple.hura.source.LernuVortaro;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;

/**
 * 整合的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Vortaro {
	private List<VortaroSource> vortaroj = Arrays.asList(new ChenVortaro(), new LernuVortaro());
	private VortaroSource googleTrans = new GoogleTranslate();

	public List<VortaroResult> query(String vorto) {
		String formatVorto = formatWord(vorto);

		try {
			List<VortaroResult> results = vortaroj.parallelStream().map(vortaro -> {
				try {
					return new VortaroResult(vortaro, vortaro.query(formatVorto));
				} catch (IOException e) {
					return null;
				}
			}).filter(Objects::nonNull).filter(result -> result.getResults() != null && !result.getResults().isEmpty())
					.collect(Collectors.toList());
			if (!results.isEmpty())
				return results;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<VortaroSourceResult> sourceResult = googleTrans.query(formatVorto);
			if (sourceResult != null && !sourceResult.isEmpty()) {
				VortaroResult result = new VortaroResult(googleTrans, sourceResult);
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
