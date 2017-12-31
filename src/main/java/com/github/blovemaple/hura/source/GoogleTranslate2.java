package com.github.blovemaple.hura.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.Language;
import com.github.blovemaple.hura.util.Conf;
import com.google.gson.Gson;

/**
 * 谷歌翻译（用Google API，post接口分行批量请求）。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslate2 implements VortaroSource {
	public static final String ZH_CN = "zh-CN";
	public static final String EO = "eo";
	public static final String EN = "en";

	private final Gson gson = new Gson();

	private String key;
	{
		try {
			key = Conf.str("private", "googleapi.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String name() {
		return "谷歌翻译";
	}

	@Override
	public VortaroSourceType type() {
		return VortaroSourceType.TRADUKILO;
	}

	@Override
	public String tip() {
		return "机翻结果，仅供参考。输入单个单词可查询词典解释。";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		String fromLang, toLang;
		switch (language) {
		case CHINESE:
			fromLang = ZH_CN;
			toLang = EO;
			break;
		case ESPERANTO:
			fromLang = EO;
			toLang = ZH_CN;
			break;
		default:
			return null;
		}
		String transed = translate(vorto, fromLang, toLang);
		return transed == null ? null : Collections.singletonList(new VortaroSourceResult(transed));
	}

	public String translate(String str, String fromLang, String toLang) throws IOException {

		StringBuilder reqJson = new StringBuilder("{'source':'" + fromLang + "','target':'" + toLang + "'");
		boolean isEmptyQ = true;
		try (BufferedReader lineReader = new BufferedReader(new StringReader(str))) {
			String line;
			while ((line = lineReader.readLine()) != null) {
				if (StringUtils.isBlank(line))
					continue;
				reqJson.append(",'q':'" + line + "'");
				isEmptyQ = false;
			}
		}
		if (isEmptyQ)
			return null;
		reqJson.append("}");

		HttpPost post = new HttpPost("https://translation.googleapis.com/language/translate/v2?key=" + key);
		post.setEntity(new StringEntity(reqJson.toString()));
//		 post.setConfig(RequestConfig.custom().setProxy(new HttpHost("127.0.0.1",
//		 1080)).build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(post);
		String responseStr = EntityUtils.toString(response.getEntity());

		GoogleTranslation2Response res = gson.fromJson(responseStr, GoogleTranslation2Response.class);
		List<String> results = res == null ? null : res.getResults();
		return results == null ? null : String.join("\n", results);
	}

	public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
		GoogleTranslate2 t = new GoogleTranslate2();
		System.out.println(t.query("mi estas\r\nhomo."));
	}

}
