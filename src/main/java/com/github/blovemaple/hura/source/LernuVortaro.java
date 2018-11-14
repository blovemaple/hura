package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.github.blovemaple.hura.Language;

/**
 * lernu词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class LernuVortaro implements VortaroSource {

	@Override
	public String name() {
		return "lernu.net词典";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		List<VortaroSourceResult> result = new ArrayList<>();

		String dictionary;
		switch(language) {
		case CHINESE:
			dictionary="zh-cn|eo";
			break;
		case ESPERANTO:
			dictionary="eo|zh-cn";
			break;
		default:
			return null;
		}
		
		Document doc = Jsoup.parse(queryRaw(vorto, dictionary));
		for (Element item : doc.select("ul.dictionary-items").first().children()) {
			if (item.hasClass("empty"))
				return null;

			String title = item.getElementsByClass("orig").first().text().trim();
			if (!title.equalsIgnoreCase(vorto))
				continue;

			StringBuilder content = new StringBuilder();

			Element structureElement = item.getElementsByClass("dictionary-structure").first();
			if (structureElement != null)
				content.append(structureElement.text().trim()).append("\n");

			Element signifoElement = item.getElementsByClass("list-unstyled").first();
			for (Element signifoItemElement : signifoElement.getElementsByTag("li"))
				content.append(signifoItemElement.text()).append("\n");
			if (content.length() == 0)
				return null;
			content.deleteCharAt(content.length() - 1);

			result.add(new VortaroSourceResult(title, content.toString()));
		}

		return result;
	}

	private String queryRaw(String vorto, String dictionary) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost("https://lernu.net/eo/vortaro");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("DictWords[dictionary]", dictionary));
		nameValuePairs.add(new BasicNameValuePair("DictWords[word]", vorto));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(post);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println(new LernuVortaro().query("vortaro"));
	}

}
