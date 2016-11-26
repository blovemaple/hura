package com.github.blovemaple.hura;

import java.util.List;

import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;

/**
 * 词典查询结果中的一个来源。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class VortaroResult {
	private VortaroSource source;
	private List<VortaroSourceResult> results;

	protected VortaroSource getSource() {
		return source;
	}

	protected void setSource(VortaroSource source) {
		this.source = source;
	}

	protected List<VortaroSourceResult> getResults() {
		return results;
	}

	protected void setResults(List<VortaroSourceResult> results) {
		this.results = results;
	}

}
