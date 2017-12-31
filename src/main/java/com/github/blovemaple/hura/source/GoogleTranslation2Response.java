package com.github.blovemaple.hura.source;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
class GoogleTranslation2Response {

	public List<String> getResults() {
		if (getData() == null || getData().getTranslations() == null || getData().getTranslations().isEmpty())
			return null;

		List<String> results = new ArrayList<>();
		for (Translation trans : getData().getTranslations()) {
			if (StringUtils.isBlank(trans.getTranslatedText()))
				continue;
			results.add(trans.getTranslatedText());
		}

		if (results.isEmpty())
			return null;
		return results;
	}

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
