package com.github.blovemaple.hura.ocr;

import static com.github.blovemaple.hura.ocr.OcrResultType.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.util.Conf;
import com.google.gson.Gson;

/**
 * Google的OCR服务。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleOcr {
	private Gson gson = new Gson();

	private String key;
	{
		try {
			key = Conf.str("private", "googleapi.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public OcrResult recognize(String imageUri) throws IOException {
		GoogleVisionResponse response = request(GoogleVisionRequest.fromImageUri(imageUri));
		String text = response.getTextResult();
		if (text != null)
			return new OcrResult(TEXT, text);
		String label = response.getLabelResult();
		if (label != null)
			return new OcrResult(LABEL, label);
		return null;
	}

	private GoogleVisionResponse request(GoogleVisionRequest req) throws IOException {
		HttpPost post = new HttpPost("https://vision.googleapis.com/v1/images:annotate?key=" + key);
		post.setEntity(new StringEntity(gson.toJson(req)));
		// post.setConfig(RequestConfig.custom().setProxy(new HttpHost("127.0.0.1",
		// 1080)).build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(post);
		String responseStr = EntityUtils.toString(response.getEntity());

		return gson.fromJson(responseStr, GoogleVisionResponse.class);
	}
}
