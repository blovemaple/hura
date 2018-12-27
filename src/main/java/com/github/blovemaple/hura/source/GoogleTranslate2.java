package com.github.blovemaple.hura.source;

import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;

import com.github.blovemaple.hura.util.Conf;
import com.github.blovemaple.hura.util.Language;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * 谷歌翻译（用Google API java client，post接口分行批量请求）。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class GoogleTranslate2 implements VortaroSource {
	public static final String ZH_CN = "zh-CN";
	public static final String EO = "eo";
	public static final String EN = "en";

	private Translate translate;

	public GoogleTranslate2() {
		try (InputStream fileIn = this.getClass()
				.getResourceAsStream(Conf.str("private", "google.application.credentials"))) {
			translate = TranslateOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(fileIn)).build()
					.getService();
		} catch (IOException e) {
			throw new RuntimeException(e);
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
		return transed == null || transed.equalsIgnoreCase(vorto) ? null
				: Collections.singletonList(new VortaroSourceResult(transed));
	}

	public String translate(String str, String fromLang, String toLang) throws IOException {
		List<String> texts = new ArrayList<>();
		try (BufferedReader lineReader = new BufferedReader(new StringReader(str))) {
			String line;
			while ((line = lineReader.readLine()) != null) {
				if (StringUtils.isBlank(line))
					continue;
				texts.add(line.trim());
			}
		}
		if (texts.isEmpty())
			return null;

		List<Translation> translations = translate.translate(texts, TranslateOption.sourceLanguage(fromLang),
				TranslateOption.targetLanguage(toLang));
		if (translations.isEmpty())
			return null;
		List<String> results = translations.stream().map(Translation::getTranslatedText).collect(toList());

		return String.join("\n", results);
	}

	public static void main(String[] args) throws ParseException, IOException, URISyntaxException {
		GoogleTranslate2 t = new GoogleTranslate2();
		System.out.println(t.query("mi\nestas esperantisto.", Language.ESPERANTO));
	}

}
