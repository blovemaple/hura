package com.github.blovemaple.hura.wordana;

import java.util.List;

import com.github.blovemaple.hura.wordana.wordcomp.WordComponent;
import com.github.blovemaple.hura.wordana.wordprop.WordProperty;

/**
 * 单词解析的结果。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class WordAnalysisResult {
	private List<WordComponent> components;
	private List<WordProperty> properties;

	public List<WordComponent> getComponents() {
		return components;
	}

	public void setComponents(List<WordComponent> components) {
		this.components = components;
	}

	public List<WordProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<WordProperty> properties) {
		this.properties = properties;
	}
}
