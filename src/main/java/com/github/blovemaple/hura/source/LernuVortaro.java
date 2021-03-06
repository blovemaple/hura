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

import com.github.blovemaple.hura.util.Language;

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
		switch (language) {
		case CHINESE:
			dictionary = "zh-cn|eo";
			break;
		case ESPERANTO:
			dictionary = "eo|zh-cn";
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
		nameValuePairs.add(new BasicNameValuePair("YII_CSRF_TOKEN",
				"U1dGNGFUQWk5a0JXNG56bnRnSFVMcWUyaG5kVF9ibHXvg1Or371hX24ENpTucajyr2eDv9_86D4z0diVZfC80A=="));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		post.setHeader("Cookie",
				"YII_CSRF_TOKEN=U1dGNGFUQWk5a0JXNG56bnRnSFVMcWUyaG5kVF9ibHXvg1Or371hX24ENpTucajyr2eDv9_86D4z0diVZfC80A%3D%3D; cookieconsent_status=dismiss; PHPSESSID=2bqilkbv7203m4315uls9tm5v6; d17e7737c3706ee5bba0546a674d817e=f4c34fc95f305f78311c099bbd38e83559b660a5a%3A4%3A%7Bi%3A0%3Bs%3A6%3A%22160224%22%3Bi%3A1%3Bs%3A10%3A%22blovemaple%22%3Bi%3A2%3Bi%3A2592000%3Bi%3A3%3Ba%3A0%3A%7B%7D%7D; lang=zh-cn");

		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(post);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println(new LernuVortaro().query("vortaro"));
	}

}
