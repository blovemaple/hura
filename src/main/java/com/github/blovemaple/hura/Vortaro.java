package com.github.blovemaple.hura;

import static com.github.blovemaple.hura.source.VortaroSourceType.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate;
import com.github.blovemaple.hura.source.LernuVortaro;
import com.github.blovemaple.hura.source.VortaroSource;
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
	 * @throws InterruptedException
	 */
	public List<VortaroResult> query(String vorto, int timeout) throws InterruptedException {
		long startTime = System.currentTimeMillis();

		String formatVorto = formatWord(vorto);

		// 开始查询所有来源
		Map<VortaroSource, CompletableFuture<VortaroResult>> allFutures = sources.stream()
				.collect(toMap(
						// key是source本身
						identity(),
						// value是future
						source ->
						// 查询
						CompletableFuture.supplyAsync(() -> source.queryWithoutException(formatVorto))
								// 生成VortaroResult
								.thenApply(results -> VortaroResult.of(source, results)),
						// 重复source，打印错误信息并取第一个结果
						(u, v) -> {
							System.err.println("Duplicated sources exist!");
							return u;
						},
						// 使用LinkedHashMap，以保证按sources的顺序
						LinkedHashMap::new));

		// 等待所有非机翻来源结果
		waitForResults(allFutures, source -> source.type() != TRADUKILO, startTime, timeout);

		// 检查有无词典来源的结果
		boolean hasVortaroResult = allFutures.values().stream().map(future -> future.getNow(null))
				.filter(Objects::nonNull).anyMatch(result -> result.getSource().type() == VORTARO);
		// 若没有词典结果，则等待所有机翻结果
		if (!hasVortaroResult) {
			waitForResults(allFutures, source -> source.type() == TRADUKILO, startTime, timeout);
		}

		// 收集结果
		List<VortaroResult> results = allFutures.values().stream()
				// 取出有效结果
				.map(future -> future.getNow(null)).filter(Objects::nonNull)
				// 如果有词典结果，则过滤掉机翻结果
				.filter(result -> result.getSource().type() != (hasVortaroResult ? TRADUKILO : VORTARO))
				// 收集
				.collect(Collectors.toList());

		// 停止未结束的查询（XXX CompletableFuture不能中断底层任务，只能把自己置为cancelled状态）
		allFutures.values().stream().filter(future -> !future.isDone()).forEach(future -> future.cancel(true));

		//返回结果
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

	/**
	 * 在allFutures中等待指定条件的source的结果，直到结果全部产生或超时。
	 */
	private void waitForResults(Map<VortaroSource, CompletableFuture<VortaroResult>> allFutures,
			Predicate<VortaroSource> sourceFilter, long startTime, int timeout) throws InterruptedException {
		try {
			CompletableFuture
					.allOf(allFutures.entrySet().stream().filter(entry -> sourceFilter.test(entry.getKey()))
							.map(Map.Entry::getValue).toArray(CompletableFuture[]::new))
					.get(startTime + timeout * 1000 - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			// 已经catch了执行异常，不可能出现
			e.printStackTrace();
		} catch (TimeoutException e) {
			// 时限已到
		}
	}

}
