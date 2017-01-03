package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.util.Collections;

/**
 * 维基词典词源。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class WiktionaryEthmology extends Wiktionary {

	public WiktionaryEthmology() {
		super(Collections.singletonList("Etymology"), false);
	}

	@Override
	public String name() {
		return "词源（维基词典）";
	}

	@Override
	public VortaroSourceType type() {
		return VortaroSourceType.EKSTRA;
	}

	public static void main(String[] args) throws IOException {
		WiktionaryEthmology w = new WiktionaryEthmology();
		System.out.println(w.query("lingvo"));
	}
}
