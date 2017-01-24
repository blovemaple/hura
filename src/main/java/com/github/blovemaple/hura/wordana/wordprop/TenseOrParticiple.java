package com.github.blovemaple.hura.wordana.wordprop;

/**
 * 动词时态或分词类型。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum TenseOrParticiple {
	INFINITIVE("不定式"), //
	PRESENT("现在时"), //
	PAST("过去时"), //
	FUTURE("将来时"), //
	JUSSIVE("命令式"), //
	CONDITIONAL("条件式"), //

	ACTIVE_PRESENT("主动现在分词"), //
	ACTIVE_PAST("主动过去分词"), //
	ACTIVE_FUTURE("主动将来分词"), //
	PASSIVE_PRESENT("被动现在分词"), //
	PASSIVE_PAST("被动过去分词"), //
	PASSIVE_FUTURE("被动将来分词"); //

	private String showName;

	private TenseOrParticiple(String showName) {
		this.showName = showName;
	}

	public String showName() {
		return showName;
	}
}
