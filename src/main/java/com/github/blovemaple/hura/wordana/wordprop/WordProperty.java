package com.github.blovemaple.hura.wordana.wordprop;

import java.util.List;

import com.github.blovemaple.hura.wordana.wordcomp.WordComponent;

/**
 * 词的属性。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public interface WordProperty {
	/**
	 * 根据词的组成部分判断是否具有此属性。
	 */
	boolean match(List<WordComponent> comps);
}
