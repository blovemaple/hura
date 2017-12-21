package com.github.blovemaple.hura.ocr;

import java.util.List;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
class GoogleVisionResponse {
	private List<Response> responses;

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public String getResult() {
		if (responses == null || responses.isEmpty())
			return null;
		Response response = responses.get(0);
		FullTextAnnotation fullTextAnnotation = response.getFullTextAnnotation();
		if (fullTextAnnotation == null)
			return null;
		return fullTextAnnotation.getText();
	}

	public static class Response {
		private FullTextAnnotation fullTextAnnotation;

		public FullTextAnnotation getFullTextAnnotation() {
			return fullTextAnnotation;
		}

		public void setFullTextAnnotation(FullTextAnnotation fullTextAnnotation) {
			this.fullTextAnnotation = fullTextAnnotation;
		}

	}

	public static class FullTextAnnotation {
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}
}
