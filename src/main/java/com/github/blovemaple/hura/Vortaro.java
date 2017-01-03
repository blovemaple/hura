package com.github.blovemaple.hura;

import static com.github.blovemaple.hura.source.VortaroSourceType.*;

import java.io.IOException;
import java.util.Arrays;
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
	private List<VortaroSource> sources = Arrays.asList( //
			new WiktionaryEthmology(), // 附加：维基词典词源
			new ChenVortaro(), // 词典：陈在伟老师的词典
			new LernuVortaro(), // 词典：lernu词典
			new GoogleTranslate() // 机翻：谷歌翻译
	);

	/**
	 * @param vorto
	 * @param timeout
	 *            最长查询时间，单位秒
	 * @return
	 */
	public List<VortaroResult> query(String vorto, int timeout) {
		long startTime = System.currentTimeMillis();

		String formatVorto = formatWord(vorto);

		// 开始查询所有来源
		Map<VortaroSource, CompletableFuture<List<VortaroSourceResult>>> allFutures = new LinkedHashMap<>();
		sources.forEach(source -> {
			CompletableFuture<List<VortaroSourceResult>> future = CompletableFuture.supplyAsync(() -> {
				try {
					return source.query(formatVorto);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			});
			allFutures.put(source, future);
		});

		// 等待结果，直到所有非机翻来源查询完毕或者到达时限
		try {
			CompletableFuture
					.allOf(sources.stream().filter(source -> source.type() != TRADUKILO).map(allFutures::get)
							.toArray(CompletableFuture[]::new))
					.get(startTime + timeout * 1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		} catch (TimeoutException e) {
			// 时限已到
		}

		// 非机翻来源查询完毕或时限已到，检查词典来源有无结果
		boolean hasVortaroResult = sources.stream().filter(source -> source.type() == VORTARO).map(allFutures::get)
				.anyMatch(future -> {
					try {
						List<VortaroSourceResult> results = future.getNow(null);
						if (results != null && !results.isEmpty())
							return true;
						return false;
					} catch (Exception e) {
						return false;
					}
				});
		if (!hasVortaroResult) {
			// 没有词典结果，等待任何一个翻译结果
			try {
				CompletableFuture
						.anyOf(sources.stream().filter(source -> source.type() == TRADUKILO).map(allFutures::get)
								.toArray(CompletableFuture[]::new))
						.get(startTime + timeout * 1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				return null;
			} catch (ExecutionException e) {
				e.printStackTrace();
				return null;
			} catch (TimeoutException e) {
				// 时限已到
			}
		}

		// 收集并返回结果
		List<VortaroResult> results = allFutures.entrySet().stream()
				// 如果有词典结果，则过滤掉机翻结果
				.filter(entry -> entry.getKey().type() != (hasVortaroResult ? TRADUKILO : VORTARO))
				// 生成结果
				.map(entry -> new VortaroResult(entry.getKey(), entry.getValue().getNow(null)))
				// 过滤掉没结果的来源
				.filter(result -> result.getResults() != null && !result.getResults().isEmpty())
				// 收集
				.collect(Collectors.toList());
		if (!results.isEmpty())
			return results;
		else
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
