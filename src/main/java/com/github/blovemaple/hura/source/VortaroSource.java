package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.util.List;

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
	 * 查询单词解释。
	 * 
	 * @param vorto
	 *            单词
	 * @return 若干条结果
	 * @throws IOException
	 */
	List<VortaroSourceResult> query(String vorto) throws IOException;

	/**
	 * 查询单词解释。出现异常时仅打印不抛出，返回没有结果。
	 * 
	 * @param vorto
	 *            单词
	 * @return 若干条结果
	 */
	default List<VortaroSourceResult> queryWithoutException(String vorto) {
		try {
			return query(vorto);
		} catch (Exception e) {
			System.err.println("Error query " + vorto + " from " + name());
			e.printStackTrace();
			return null;
		}
	}
}
