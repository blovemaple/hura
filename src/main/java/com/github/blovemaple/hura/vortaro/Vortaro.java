package com.github.blovemaple.hura.vortaro;

import java.io.IOException;

/**
 * 词典接口。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface Vortaro {
	/**
	 * 查询单词解释。
	 * 
	 * @param vorto
	 *            单词
	 * @return 解释
	 * @throws IOException
	 */
	String query(String vorto) throws IOException;
}
