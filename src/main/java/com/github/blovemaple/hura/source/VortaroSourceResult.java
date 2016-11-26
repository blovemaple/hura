package com.github.blovemaple.hura.source;

/**
 * 一个词典来源的一条查询结果。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class VortaroSourceResult {
	private String title;
	private String content;

	public VortaroSourceResult(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public VortaroSourceResult(String content) {
		this(null, content);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "[" + title + ", " + content + "]";
	}

}