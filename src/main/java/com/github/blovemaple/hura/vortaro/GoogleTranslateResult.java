package com.github.blovemaple.hura.vortaro;

import java.util.List;

/**
 * 谷歌翻译查询结果。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslateResult {

	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data {
		private List<Translation> translations;

		public List<Translation> getTranslations() {
			return translations;
		}

		public void setTranslations(List<Translation> translations) {
			this.translations = translations;
		}
	}

	public static class Translation {
		private String translatedText;

		public String getTranslatedText() {
			return translatedText;
		}

		public void setTranslatedText(String translatedText) {
			this.translatedText = translatedText;
		}
	}
}
