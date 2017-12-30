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

	public String getTextResult() {
		if (responses == null || responses.isEmpty())
			return null;
		Response response = responses.get(0);
		FullTextAnnotation fullTextAnnotation = response.getFullTextAnnotation();
		if (fullTextAnnotation == null || fullTextAnnotation.getText() == null
				|| fullTextAnnotation.getText().isEmpty())
			return null;
		return fullTextAnnotation.getText();
	}

	public String getLabelResult() {
		if (responses == null || responses.isEmpty())
			return null;
		Response response = responses.get(0);
		List<LabelAnnotation> labelAnnotations = response.getLabelAnnotations();
		if (labelAnnotations == null || labelAnnotations.isEmpty())
			return null;
		LabelAnnotation labelAnnotation = labelAnnotations.get(0);
		if (labelAnnotation == null || labelAnnotation.getDescription() == null
				|| labelAnnotation.getDescription().isEmpty())
			return null;
		return labelAnnotation.getDescription();
	}

	public static class Response {
		private FullTextAnnotation fullTextAnnotation;
		private List<LabelAnnotation> labelAnnotations;

		public FullTextAnnotation getFullTextAnnotation() {
			return fullTextAnnotation;
		}

		public void setFullTextAnnotation(FullTextAnnotation fullTextAnnotation) {
			this.fullTextAnnotation = fullTextAnnotation;
		}

		public List<LabelAnnotation> getLabelAnnotations() {
			return labelAnnotations;
		}

		public void setLabelAnnotations(List<LabelAnnotation> labelAnnotations) {
			this.labelAnnotations = labelAnnotations;
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

	public static class LabelAnnotation {
		private String mid;
		private String description;
		private Double score;

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Double getScore() {
			return score;
		}

		public void setScore(Double score) {
			this.score = score;
		}

	}
}
