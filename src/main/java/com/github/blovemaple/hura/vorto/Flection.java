package com.github.blovemaple.hura.vorto;

import java.util.List;

/**
 * 单词屈折形式。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum Flection {
	PLURALO_AND_AKUZATIVO("复数、宾格（或度量、时间点、方向等）", "jn", "", "a", "o"), //
	PLURALO("复数", "j", "", "a", "o"), //
	AKUZATIVO("宾格（或度量、时间点、方向等）", "n", "", "a", "o", "j"), //

	PREZENCO("动词现在时", "as", "i"), //
	PRETERITO("动词过去时", "is", "i"), //
	FUTURO("动词将来时", "os", "i"), //
	VOLITIVO("意愿式（命令式）动词", "u", "i"), //
	KONDICIONALO("假定式动词", "us", "i"), //

	ACTIVE_PARTICIPO_ANTA("形容词形式的现在主动分词", "anta", "i"), //
	ACTIVE_PARTICIPO_INTA("形容词形式的过去主动分词", "inta", "i"), //
	ACTIVE_PARTICIPO_ONTA("形容词形式的将来主动分词", "onta", "i"), //
	ACTIVE_PARTICIPO_ATA("形容词形式的现在被动分词", "ata", "i"), //
	ACTIVE_PARTICIPO_ITA("形容词形式的过去被动分词", "ita", "i"), //
	ACTIVE_PARTICIPO_OTA("形容词形式的将来被动分词", "ota", "i"), //
	ACTIVE_PARTICIPO_ANTE("副词形式的现在主动分词", "ante", "i"), //
	ACTIVE_PARTICIPO_INTE("副词形式的过去主动分词", "inte", "i"), //
	ACTIVE_PARTICIPO_ONTE("副词形式的将来主动分词", "onte", "i"), //
	ACTIVE_PARTICIPO_ATE("副词形式的现在被动分词", "ate", "i"), //
	ACTIVE_PARTICIPO_ITE("副词形式的过去被动分词", "ite", "i"), //
	ACTIVE_PARTICIPO_OTE("副词形式的将来被动分词", "ote", "i"), //
	ACTIVE_PARTICIPO_ANTO("名词形式的现在主动分词", "anto", "i"), //
	ACTIVE_PARTICIPO_INTO("名词形式的过去主动分词", "into", "i"), //
	ACTIVE_PARTICIPO_ONTO("名词形式的将来主动分词", "onto", "i"), //
	ACTIVE_PARTICIPO_ATO("名词形式的现在被动分词", "ato", "i"), //
	ACTIVE_PARTICIPO_ITO("名词形式的过去被动分词", "ito", "i"), //
	ACTIVE_PARTICIPO_OTO("名词形式的将来被动分词", "oto", "i"), //

	;
	private String name;
	private String ending;
	private String baseFormEnding;
	private List<String> legalPrevious;

	private Flection(String name, String ending, String baseFormEnding,String... legalPrevious) {
		this.name = name;
		this.ending = ending;
		this.baseFormEnding = baseFormEnding;
		this.legalPrevious = List.of(legalPrevious);
	}

	public String getName() {
		return name;
	}

	public String getEnding() {
		return ending;
	}

	public String getBaseFormEnding() {
		return baseFormEnding;
	}

	public List<String> getLegalPrevious() {
		return legalPrevious;
	}

}
