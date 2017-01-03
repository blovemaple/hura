package com.github.blovemaple.hura.source;

import static com.github.blovemaple.hura.util.MyUtils.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 谷歌翻译（模拟网页请求）。
 * 
 * @deprecated 这种方式从VPS上请求被拒，不知道为什么。所以改成用收费的Google API，在{@code GoogleTranslate}。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslateCracked implements VortaroSource {

	private static final String ZH_CN = "zh-CN";
	private static final String EO = "eo";

	private final Pattern TRANS_TARGET_PATTERN = Pattern.compile("\".*?\"");

	@Override
	public String name() {
		return "谷歌翻译";
	}

	@Override
	public VortaroSourceType type() {
		return VortaroSourceType.TRADUKILO;
	}

	@Override
	public List<VortaroSourceResult> query(String vorto) throws IOException {
		try {
			boolean hasChinese = hasChinese(vorto);
			String raw = queryRaw(vorto, hasChinese ? ZH_CN : EO, hasChinese ? EO : ZH_CN);
			Matcher matcher = TRANS_TARGET_PATTERN.matcher(raw);
			if (matcher.find()) {
				String matched = matcher.group();
				String transed = matched.substring(1, matched.length() - 1);
				if (transed.equals(vorto))
					return null;

				StringBuilder res = new StringBuilder();
				res.append(transed);
				res.append("\n\n——由于没有在词典中查到，此结果来自谷歌翻译，机翻不够精确仅供参考 :)");
				return Collections.singletonList(new VortaroSourceResult(res.toString()));
			} else
				return null;
		} catch (ParseException | URISyntaxException e) {
			throw new IOException(e);
		}
	}

	private String queryRaw(String vorto, String fromLang, String toLang)
			throws ParseException, IOException, URISyntaxException {
		String tk = genTk(vorto.trim());
		HttpGet get = new HttpGet(
				"http://translate.google.cn/translate_a/single?client=t&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&swap=1&otf=1&ssel=5&tsel=5&kc=1&sl="
						+ fromLang + "&tl=" + toLang + "&hl=zh-CN&tk=" + tk + "&q="
						+ URLEncoder.encode(vorto, "UTF-8"));
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}

	public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
		GoogleTranslateCracked t = new GoogleTranslateCracked();
		System.out.println(t.query("Mi ne estas."));
	}

	private String genTk(String text) {
		try {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
			SimpleBindings bindings = new SimpleBindings();
			bindings.put("a", text);
			Object result = nashorn.eval("load('src/main/resources/tk.js')", bindings);
			return result.toString();
		} catch (ScriptException e) {
			// 不会出现
			e.printStackTrace();
			return null;
		}
	}

}
