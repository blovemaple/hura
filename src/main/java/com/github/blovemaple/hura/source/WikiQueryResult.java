package com.github.blovemaple.hura.source;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * MediaWiki API Query结果。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class WikiQueryResult {
	private QueryData query;

	public QueryData getQuery() {
		return query;
	}

	public void setQuery(QueryData query) {
		this.query = query;
	}

	public static class QueryData {
		private Map<Long, PageData> pages;

		public Map<Long, PageData> getPages() {
			return pages;
		}

		public void setPages(Map<Long, PageData> pages) {
			this.pages = pages;
		}

	}

	public static class PageData {
		private String title;
		private List<RevisionData> revisions;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<RevisionData> getRevisions() {
			return revisions;
		}

		public void setRevisions(List<RevisionData> revisions) {
			this.revisions = revisions;
		}

	}

	public static class RevisionData {
		@SerializedName("*")
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}
}
