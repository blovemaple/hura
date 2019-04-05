package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.util.List;

import com.github.blovemaple.hura.util.Language;

/**
 * 词典来源接口。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface VortaroSource {
	/**
	 * 返回词典来源名称。
	 */
	String name();

	/**
	 * 返回词典来源类型。默认为词典。
	 */
	default VortaroSourceType type() {
		return VortaroSourceType.VORTARO;
	}

	/**
	 * 返回词典来源的注释。
	 */
	default String tip() {
		return null;
	}

	/**
	 * 是否有详细解释。
	 */
	default boolean hasDetail() {
		return false;
	}

	/**
	 * 查询单词解释。
	 * 
	 * @param vorto
	 *            单词
	 * @param isDetail
	 *            是否查询详细解释
	 * @return 若干条结果
	 * @throws IOException
	 */
	default List<VortaroSourceResult> query(String vorto, boolean isDetail) throws IOException {
		return isDetail ? queryDetail(vorto) : query(vorto);
	}

	/**
	 * 查询单词解释。
	 * 
	 * @param vorto
	 *            单词
	 * @return 若干条结果
	 * @throws IOException
	 */
	default List<VortaroSourceResult> query(String vorto) throws IOException {
		return query(vorto, Language.determine(vorto));
	}

	/**
	 * 查询单词详细解释。
	 * 
	 * @param vorto
	 *            单词
	 * @return 若干条结果
	 * @throws IOException
	 */
	default List<VortaroSourceResult> queryDetail(String vorto) throws IOException {
		return queryDetail(vorto, Language.determine(vorto));
	}

	/**
	 * 查询单词解释（指定语言）。
	 * 
	 * @param vorto
	 *            单词
	 * @param language
	 *            语言
	 * @return 若干条结果
	 * @throws IOException
	 */
	List<VortaroSourceResult> query(String vorto, Language language) throws IOException;

	/**
	 * 查询单词详细解释（指定语言）。默认调用query。
	 * 
	 * @param vorto
	 *            单词
	 * @param language
	 *            语言
	 * @return 若干条结果
	 * @throws IOException
	 */
	default List<VortaroSourceResult> queryDetail(String vorto, Language language) throws IOException {
		return query(vorto, language);
	}

	/**
	 * 查询单词解释。出现异常时仅打印不抛出，返回没有结果。
	 */
	default List<VortaroSourceResult> queryWithoutException(String vorto, Language language) {
		try {
			return language == null ? query(vorto) : query(vorto, language);
		} catch (Exception e) {
			System.err.println("Error query " + vorto + " from " + name());
			e.printStackTrace();
			return null;
		}
	}
}
