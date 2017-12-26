package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.Language;
import com.github.blovemaple.hura.util.Conf;
import com.google.gson.Gson;

/**
 * 谷歌翻译（用Google API）。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslate implements VortaroSource {
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
		return Collections.singletonList(new VortaroSourceResult(transed));
	}

	public String translate(String str, String fromLang, String toLang) throws IOException {
		try {
			String raw = queryRaw(str, fromLang, toLang);
			GoogleTranslateResult result = gson.fromJson(raw, GoogleTranslateResult.class);
			String transed = result.getData().getTranslations().get(0).getTranslatedText();

			if (transed.equalsIgnoreCase(str))
				return null;

			return transed;
		} catch (ParseException | URISyntaxException e) {
			throw new IOException(e);
		}
	}

	private String queryRaw(String vorto, String fromLang, String toLang)
			throws ParseException, IOException, URISyntaxException {
		HttpGet get = new HttpGet("https://www.googleapis.com/language/translate/v2?source=" + fromLang + "&target="
				+ toLang + "&key=" + key + "&q=" + URLEncoder.encode(vorto, "UTF-8"));
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}

	public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
		GoogleTranslate t = new GoogleTranslate();
		System.out.println(t.query("Mi ne estas."));
	}

}
