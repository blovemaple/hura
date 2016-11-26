package com.github.blovemaple.hura.source;

import static com.github.blovemaple.hura.util.MyUtils.*;

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

import com.github.blovemaple.hura.util.Conf;
import com.google.gson.Gson;

/**
 * 谷歌翻译（用Google API）。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslate implements VortaroSource {
	private static final String ZH_CN = "zh-CN";
	private static final String EO = "eo";

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
	public String tip() {
		return "机翻结果，仅供参考";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto) throws IOException {
		try {
			boolean hasChinese = hasChinese(vorto);
			String raw = queryRaw(vorto, hasChinese ? ZH_CN : EO, hasChinese ? EO : ZH_CN);
			GoogleTranslateResult result = gson.fromJson(raw, GoogleTranslateResult.class);
			String transed = result.getData().getTranslations().get(0).getTranslatedText();

			if (transed.equalsIgnoreCase(vorto))
				return null;

			return Collections.singletonList(new VortaroSourceResult(transed));
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
