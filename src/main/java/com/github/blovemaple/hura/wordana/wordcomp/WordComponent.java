package com.github.blovemaple.hura.wordana.wordcomp;

import java.util.List;

/**
 * 单词的组成部分。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface WordComponent {
	//TODO 普通词根（名词性、动词性、形容词性） 前缀 普通后缀 分词后缀 小品词（多种词性） 词性词尾 时态词尾 特殊词尾（j/n）
	// ( 前缀* (前缀|普通词根|普通后缀) (普通后缀|分词后缀)* 词性词尾? )* ( 前缀* (前缀|普通词根|普通后缀) (普通后缀|分词后缀)* (词性词尾|时态词尾) 特殊词尾* )
	//同一comp在临近的一组comp中只能出现一次
	//j/n/jn必须在最后，j前只能是o/a/ua尾表解词，n前只能是o/a/e/人称代词/uoae尾表解词
	
	/**
	 * 返回字面（判断用）。
	 */
	String literal();

	/**
	 * 返回字面（展示用）。
	 */
	String showLiteral();

	/**
	 * 返回解释。
	 */
	String desc();

	/**
	 * 根据之前的部分判断添加此部分是否合法。
	 * 
	 * @param previousComps
	 *            之前的部分
	 * @return 是否合法
	 */
	boolean validate(List<WordComponent> previousComps);
}
