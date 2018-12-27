package com.github.blovemaple.hura.abonkonto;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.source.GoogleTranslate2;
import com.github.blovemaple.hura.source.LernuVortaro;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.WiktionaryEthmology;
import com.github.blovemaple.hura.util.Language;
import com.github.blovemaple.hura.util.MyUtils;
import com.github.blovemaple.hura.vorto.Lemmatization;

/**
 * 整合的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Vortaro {
	private VortaroSource ethmologySource = new WiktionaryEthmology();
	private VortaroSource translationSource = new GoogleTranslate2();
	private List<VortaroSource> dictionarySources = Arrays.asList(new ChenVortaro(), new LernuVortaro());
	private VortaroSource lemmaSource = new Lemmatization();

	/**
	 * @param vorto
	 * @param language
	 * @param timeout
	 *                     最长查询时间，单位毫秒
	 * @return
	 * @throws InterruptedException
	 */
	public List<VortaroResult> query(String vorto, Language language, int timeout) throws InterruptedException {
		long startTime = System.currentTimeMillis();

		String formatVorto = MyUtils.formatWord(vorto);

		String baseForm = baseForm(formatVorto);

		// 使用LinkedHashMap保存futures，以保证按sources的顺序
		Map<QueryInfo, CompletableFuture<VortaroResult>> allFutures = new LinkedHashMap<>();

		// 原型查询词源、词典、翻译来源、单词解析
		startQuery(baseForm, ethmologySource, language, allFutures);
		dictionarySources.forEach(source -> startQuery(baseForm, source, language, allFutures));
		startQuery(baseForm, translationSource, language, allFutures);
		startQuery(formatVorto, lemmaSource, language, allFutures);

		// 本词查询词典、翻译来源
		if (!baseForm.equals(formatVorto)) {
			dictionarySources.forEach(source -> startQuery(formatVorto, source, language, allFutures));
			startQuery(formatVorto, translationSource, language, allFutures);
		}

		// 收集结果
		List<VortaroResult> results = new ArrayList<>();

		// 第1优先：本词词典结果
		if (results.isEmpty()) {
			results.addAll(waitForResults(allFutures, formatVorto, dictionarySources::contains, startTime, timeout));
		}

		// 第2优先：原形词典结果
		if (results.isEmpty()) {
			results.addAll(waitForResults(allFutures, baseForm, dictionarySources::contains, startTime, timeout));
		}

		// 第3优先：本词翻译结果
		if (results.isEmpty()) {
			results.addAll(waitForResults(allFutures, formatVorto, s -> s == translationSource, startTime, timeout));
		}

		// 第4优先：原形翻译结果
		if (results.isEmpty()) {
			results.addAll(waitForResults(allFutures, baseForm, s -> s == translationSource, startTime, timeout));
		}

		// 如果有结果，添加单词解析和原形词源
		if (!results.isEmpty()) {
			waitForResults(allFutures, baseForm, s -> s == ethmologySource, startTime, timeout)
					.forEach(result -> results.add(0, result));
			waitForResults(allFutures, formatVorto, s -> s == lemmaSource, startTime, timeout)
					.forEach(result -> results.add(0, result));
		}

		// 停止未结束的查询（XXX CompletableFuture不能中断底层任务，只能把自己置为cancelled状态）
		allFutures.values().stream().filter(future -> !future.isDone()).forEach(future -> future.cancel(true));

		return results.isEmpty() ? null : results;
	}

	private static class QueryInfo {
		String vorto;
		VortaroSource source;

		QueryInfo(String vorto, VortaroSource source) {
			this.vorto = vorto;
			this.source = source;
		}
	}

	private static String baseForm(String vorto) {
		Lemmatization lemma = Lemmatization.parse(vorto);
		return lemma.getBaseForm();
	}

	private static void startQuery(String vorto, VortaroSource source, Language language,
			Map<QueryInfo, CompletableFuture<VortaroResult>> allFutures) {
		QueryInfo queryInfo = new QueryInfo(vorto, source);
		CompletableFuture<VortaroResult> future =
				// future第一步: 查询
				CompletableFuture.supplyAsync(() -> source.queryWithoutException(vorto, language))
						// future第二步: 生成VortaroResult
						.thenApply(results -> VortaroResult.of(vorto, source, results));

		allFutures.put(queryInfo, future);
	}

	/**
	 * 在allFutures中等待指定条件的source的结果，直到结果全部产生或超时，超时时返回当前已经产生的结果。
	 */
	private List<VortaroResult> waitForResults(Map<QueryInfo, CompletableFuture<VortaroResult>> allFutures,
			String vorto, Predicate<VortaroSource> sourceFilter, long startTime, int timeout)
			throws InterruptedException {
		// 过滤出符合条件的futures
		List<CompletableFuture<VortaroResult>> matchedFutures = new ArrayList<>();
		allFutures.forEach((queryInfo, future) -> {
			if (sourceFilter.test(queryInfo.source) && queryInfo.vorto.equals(vorto))
				matchedFutures.add(future);
		});
		if (matchedFutures.isEmpty())
			// 没有符合条件的future
			return Collections.emptyList();

		try {
			// 等待所有符合条件的futures
			CompletableFuture.allOf(matchedFutures.toArray(new CompletableFuture[] {}))
					.get(startTime + timeout - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			// 已经catch了执行异常，不可能出现
			e.printStackTrace();
			return Collections.emptyList();
		} catch (TimeoutException e) {
			// 时限已到
		}
		// 返回已生成的结果
		return matchedFutures.stream().map(future -> future.getNow(null)).filter(Objects::nonNull).collect(toList());
	}

}
