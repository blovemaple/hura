package com.github.blovemaple.hura.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.source.WikiQueryResult.PageData;
import com.github.blovemaple.hura.source.WikiQueryResult.QueryData;
import com.github.blovemaple.hura.source.WikiQueryResult.RevisionData;
import com.google.gson.Gson;

/**
 * 维基词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Wiktionary implements VortaroSource {

	private final Gson gson = new Gson();

	@Override
	public String name() {
		return "维基词典";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto) throws IOException {
		try {
			WikiQueryResult queryResult = queryResult(vorto);
			String pageContent = getPageContent(queryResult, vorto);
			return Collections.singletonList(new VortaroSourceResult(parseFromContent(pageContent)));
		} catch (URISyntaxException e) {
			// 不可能
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private WikiQueryResult queryResult(String vorto) throws URISyntaxException, ParseException, IOException,
			KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return gson.fromJson(queryRaw(vorto), WikiQueryResult.class);
	}

	public String queryRaw(String vorto) throws ParseException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder("https://en.wiktionary.org/w/api.php");
		builder.setParameter("action", "query") //
				.setParameter("prop", "revisions") //
				.setParameter("rvprop", "content") //
				// .setParameter("rvlimit", "1") //
				.setParameter("rvexpandtemplates", "1") //
				.setParameter("format", "json") //
				.setParameter("titles", vorto);
		builder.setCharset(Charset.forName("UTF-8"));
		HttpGet get = new HttpGet(builder.build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		return EntityUtils.toString(response.getEntity());
	}

	// debug
	public String queryRawDebug(String vorto) throws ParseException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder("http://chentong.ren/hura/rawwiki");
		builder.setParameter("vorto", vorto);

		HttpGet get = new HttpGet(builder.build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		return EntityUtils.toString(response.getEntity());
	}

	private String getPageContent(WikiQueryResult queryResult, String vorto) {
		if (queryResult == null)
			return null;

		QueryData queryData = queryResult.getQuery();
		if (queryData == null)
			return null;

		Map<Long, PageData> pages = queryData.getPages();
		if (pages == null || pages.isEmpty())
			return null;

		String pageContent = null;
		for (PageData page : pages.values()) {
			if (page.getTitle().equals(vorto)) {
				List<RevisionData> revs = page.getRevisions();
				if (revs != null && !revs.isEmpty()) {
					pageContent = revs.get(0).getContent();
					break;
				}
			}
		}
		if (pageContent == null || pageContent.isEmpty())
			return null;

		return pageContent;
	}

	private static final Pattern TITLE_PATTERN = Pattern.compile("^\\s*==\\s*([^=]+?)\\s*==\\s*$");
	private static final Pattern SUBTITLE_PATTERN = Pattern.compile("^\\s*=+(.+?)=+\\s*$");
	private static final List<String> VALID_SUBTITLES = Arrays.asList("Adjective", "Adverb", "Conjunction",
			"Determiner", "Interjection", "Prefix", "Suffix", "Noun", "Numeral", "Particle", "Phrase", "Preposition",
			"Pronoun", "Verb", "Etymology");

	private String parseFromContent(String pageContent) throws IOException {
		if (pageContent == null || pageContent.isEmpty())
			return null;

		String parsingStr = pageContent;
		parsingStr = pageContent.replaceAll("<.+?>", "") // 去掉html标签
				.replaceAll("\\[\\[(Category:|File:)[^\\[\\]]+?\\]\\]", "") // 去掉分类和文件
				.replaceAll("\\[\\[[^\\[\\]]+?\\|([^\\[\\]]+?)\\]\\]", "$1") // 把带字面的内部链接替换成字面
				.replaceAll("\\[\\[([^\\[\\]]+?)\\]\\]", "$1") // 把内部链接去掉方括号
				.replaceAll("\\[[^\\[\\]]+? ([^\\[\\]]+?)\\]", "$1") // 把带字面的外部链接替换成字面
				.replaceAll("\\[[^\\[\\]]+?\\]", "") // 去掉外部链接
				.replaceAll("'''", "").replaceAll("''", ""); // 去掉斜体和粗体标记
		parsingStr = StringEscapeUtils.unescapeJson(parsingStr);
		parsingStr = StringEscapeUtils.unescapeHtml4(parsingStr);
		parsingStr = StringEscapeUtils.unescapeJava(parsingStr);

		System.out.println(parsingStr);

		BufferedReader strReader = new BufferedReader(new StringReader(parsingStr));
		String line;
		String crtSubtitle = "";
		boolean isValidTitle = false;
		boolean isValidSubtitle = false;
		int lineIndex = 1;
		StringBuilder resultStr = new StringBuilder();
		while ((line = strReader.readLine()) != null) {
			if (line.isEmpty())
				continue;

			Matcher titleMatcher = TITLE_PATTERN.matcher(line);
			if (titleMatcher.matches()) {
				// 是语言标题
				String title = titleMatcher.group(1);
				if ("Esperanto".equals(title))
					isValidTitle = true;
				else
					isValidTitle = false;
				isValidSubtitle = false;
				continue;
			}

			if (!isValidTitle)
				continue;

			Matcher subtitleMatcher = SUBTITLE_PATTERN.matcher(line);
			if (subtitleMatcher.find()) {
				// 是子标题
				String subtitle = subtitleMatcher.group(1);
				if (VALID_SUBTITLES.contains(subtitle)) {
					crtSubtitle = subtitle;
					isValidSubtitle = true;
					lineIndex = 1;
					if (resultStr.length() > 0)
						resultStr.append("\n");
					resultStr.append("[").append(crtSubtitle).append("]\n");
				} else {
					isValidSubtitle = false;
				}
				continue;
			}

			if (!isValidSubtitle)
				continue;

			switch (crtSubtitle) {
			case "Etymology":
				resultStr.append(line).append("\n");
				break;
			default:
				if (line.startsWith("*"))
					resultStr.append("▪").append(line.substring(1).trim()).append("\n");
				else if (line.startsWith("#") && !line.startsWith("#*") && !line.startsWith("#:"))
					resultStr.append(lineIndex++).append(". ").append(line.substring(1).trim()).append("\n");
			}
		}

		if (resultStr.length() == 0)
			return null;

		return resultStr.toString();
	}

	public static void main(String[] args) throws IOException {
		Wiktionary w = new Wiktionary();
		System.out.println(w.query("tablo"));
	}
}
