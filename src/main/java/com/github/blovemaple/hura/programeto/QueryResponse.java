package com.github.blovemaple.hura.programeto;

import java.util.List;

import com.github.blovemaple.hura.source.VortaroSourceResult;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class QueryResponse {
	private List<VortaroSourceResult> results;

	public static QueryResponse empty() {
		return new QueryResponse(List.of());
	}

	public QueryResponse(List<VortaroSourceResult> results) {
		this.results = results;
	}

	public List<VortaroSourceResult> getResults() {
		return results;
	}

	public void setResults(List<VortaroSourceResult> results) {
		this.results = results;
	}

}
