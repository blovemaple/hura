package com.github.blovemaple.hura;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate;
import com.github.blovemaple.hura.source.LernuVortaro;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.source.WiktionaryEthmology;

/**
 * 整合的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Vortaro {
	private List<VortaroSource> vortaroj = Arrays.asList(new WiktionaryEthmology(), new ChenVortaro(),
			new LernuVortaro());
	private VortaroSource googleTrans = new GoogleTranslate();

	/**
	 * @param vorto
	 * @param timeout
	 *            最长查询时间，单位秒
	 * @return
	 */
	public List<VortaroResult> query(String vorto, int timeout) {
		long startTime = System.currentTimeMillis();

		String formatVorto = formatWord(vorto);

		// 开始查询所有词典、翻译
		Map<VortaroSource, CompletableFuture<List<VortaroSourceResult>>> vortaroFutures = new LinkedHashMap<>();
		vortaroj.forEach(vortaro -> {
			CompletableFuture<List<VortaroSourceResult>> future = CompletableFuture.supplyAsync(() -> {
				try {
					return vortaro.query(formatVorto);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			});
			vortaroFutures.put(vortaro, future);
		});

		CompletableFuture<List<VortaroSourceResult>> googleTransFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return googleTrans.query(formatVorto);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		});

		// 等待结果，直到所有词典查询完毕或者到达时限
		CompletableFuture<Void> allVortarojFuture = CompletableFuture
				.allOf(vortaroFutures.values().stream().toArray(CompletableFuture[]::new));
		try {
			allVortarojFuture.get(startTime + timeout * 1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		} catch (TimeoutException e) {
		}

		// 查询完毕或时限已到，检查词典已有结果
		List<VortaroResult> vortaroResults = vortaroFutures.entrySet().stream()
				.map(entry -> new VortaroResult(entry.getKey(), entry.getValue().getNow(null)))
				.filter(result -> result.getResults() != null && !result.getResults().isEmpty())
				.collect(Collectors.toList());

		if (!vortaroResults.isEmpty()) {
			// 有词典有结果，返回词典结果
			return vortaroResults;
		} else {
			// 词典都没有结果，等待并返回翻译结果
			List<VortaroSourceResult> googleTransResult = null;
			try {
				googleTransResult = googleTransFuture.get(startTime + timeout * 1000 - System.currentTimeMillis(),
						TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				return null;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return null;
			} catch (TimeoutException e) {
				// 翻译超时，返回null
				return null;
			}
			if (googleTransResult != null && !googleTransResult.isEmpty()) {
				return Collections.singletonList(new VortaroResult(googleTrans, googleTransResult));
			} else {
				// 翻译也没有结果，返回null
				return null;
			}
		}
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
