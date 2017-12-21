package com.github.blovemaple.hura.ocr;

import java.util.Collections;
import java.util.List;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
class GoogleVisionRequest {

	public static GoogleVisionRequest fromImageUri(String imageUri) {
		GoogleVisionRequest req = new GoogleVisionRequest();
		req.setRequests(Collections.singletonList(new Request(new Image(new ImageSource(imageUri)),
				Collections.singletonList(new Feature("TEXT_DETECTION", 1)))));
		return req;
	}

	private List<Request> requests;

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public static class Request {
		private Image image;
		private List<Feature> features;

		public Request(Image image, List<Feature> features) {
			this.image = image;
			this.features = features;
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		public List<Feature> getFeatures() {
			return features;
		}

		public void setFeatures(List<Feature> features) {
			this.features = features;
		}
	}

	public static class Image {
		private ImageSource source;

		public Image(ImageSource source) {
			this.source = source;
		}

		public ImageSource getSource() {
			return source;
		}

		public void setSource(ImageSource source) {
			this.source = source;
		}

	}

	public static class ImageSource {
		private String imageUri;

		public ImageSource(String imageUri) {
			this.imageUri = imageUri;
		}

		public String getImageUri() {
			return imageUri;
		}

		public void setImageUri(String imageUri) {
			this.imageUri = imageUri;
		}

	}

	public static class Feature {
		private String type;
		private int maxResults;

		public Feature(String type, int maxResults) {
			this.type = type;
			this.maxResults = maxResults;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getMaxResults() {
			return maxResults;
		}

		public void setMaxResults(int maxResults) {
			this.maxResults = maxResults;
		}

	}

}
