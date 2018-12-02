package com.github.blovemaple.hura.abonkonto;

import java.util.List;

import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;

/**
 * 词典查询结果中的一个来源。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class VortaroResult {
	private String vorto;
	private VortaroSource source;
	private List<VortaroSourceResult> results;

	public static VortaroResult of(String vorto, VortaroSource source, List<VortaroSourceResult> results) {
		if (results == null || results.isEmpty())
			return null;
		return new VortaroResult(vorto, source, results);
	}

	public VortaroResult(String vorto, VortaroSource source, List<VortaroSourceResult> results) {
		this.source = source;
		this.results = results;
	}

	public String getVorto() {
		return vorto;
	}

	public void setVorto(String vorto) {
		this.vorto = vorto;
	}

	public VortaroSource getSource() {
		return source;
	}

	public void setSource(VortaroSource source) {
		this.source = source;
	}

	public List<VortaroSourceResult> getResults() {
		return results;
	}

	public void setResults(List<VortaroSourceResult> results) {
		this.results = results;
	}

}
