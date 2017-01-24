package com.github.blovemaple.hura.wordana.wordprop;

/**
 * 词性。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public enum PartOfSpeech {
	NOUN("名词"), //
	VERB("动词"), //
	ADJECTIVE("形容词"), //
	ADVERB("副词"), //
	NUMERAL("数词"), //
	ARTICLE("冠词"), //
	CORRELATIVE("相关词"), //
	PERSONAL_PRONOUN("人称代词"), //
	POSSESIVE_PRONOUN("物主代词"), //
	PROPOSITION("介词"), //
	CONJUNCTION("连词"), //
	INTERJECTION("叹词"), //
	PARTICLE("小品词"), //
	ROOT("词根"), //
	PRIFIX("前缀"), //
	SUFFIX("后缀"), //
	ENDING("词尾"); //

	private String showName;

	private PartOfSpeech(String showName) {
		this.showName = showName;
	}

	public String showName() {
		return showName;
	}
}
