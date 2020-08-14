package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.blovemaple.hura.util.CssInliner;
import com.github.blovemaple.hura.util.Language;
import com.google.gson.Gson;

/**
 * Vortaro.net
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Piv implements VortaroSource {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(Piv.class);

	HttpClientContext httpContext = HttpClientContext.create();
	HttpClient http = HttpClients.createSystem();
	private String css;

	private Gson gson = new Gson();

	@Override
	public String name() {
		return "PIV(vortaro.net)";
	}

	@Override
	public boolean hasDetail() {
		return true;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("CONTENT: " + new Piv().queryDetail("lerni"));
		// Files.writeString(Paths.get("E:\\out.html"), new
		// Piv().queryDetail("vortaro").get(0).getContent());
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		Element html = queryRaw(vorto, language);
		if (html == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		simplify(html, result);
		return List.of(new VortaroSourceResult(result.toString()));
	}

	private void simplify(Node node, StringBuilder resultString) {
		if (node instanceof Element) {
			// div节点前换行
			if (resultString.length() > 0 && ((Element) node).tagName().equals("div"))
				resultString.append("\n");

			// 循环递归子节点
			for (Node child : node.childNodes()) {

				// 排除上标节点
				if (child instanceof Element && ((Element) child).tagName().equals("sup"))
					continue;

				// 排除Vortoreferenco之后的部分（TODO 这部分在新版暂时没找到对应的class）
				if (child instanceof Element && ((Element) child).hasClass("Vortoreferenco"))
					break;

				// 排除斜体之后的部分（斜体表示详细解释或者举例）
				if (resultString.lastIndexOf("(") <= resultString.lastIndexOf(")") // 没有在括号里
						&& child instanceof Element && ((Element) child).tagName().equals("i")) {
					// 因去掉了斜体部分，把末尾的冒号换成句号，如果没有句号就加句号
					for (int i = resultString.length() - 1; i >= 0; i--) {
						if (resultString.charAt(i) == ' ')
							continue;
						else if (resultString.charAt(i) == ':') {
							resultString.setCharAt(i, '.');
							if (resultString.length() > i + 1)
								resultString.delete(i + 1, resultString.length());
							break;
						} else
							break;
					}

					break;
				}

				// 递归子节点
				simplify(child, resultString);

				// <b>纯数字标签后加点
				if (child instanceof Element && ((Element) child).tagName().equals("b")
						&& Character.isDigit(resultString.charAt(resultString.length() - 1)))
					resultString.append('.');
			}

			// 因去掉了举例，把末尾的冒号换成句号
			if (resultString.length() > 0 && resultString.charAt(resultString.length() - 1) == ':')
				resultString.setCharAt(resultString.length() - 1, '.');

		} else {
			if (node.outerHtml().isBlank())
				return;
			node.html(resultString);
		}
	}

	@Override
	public List<VortaroSourceResult> queryDetail(String vorto, Language language) throws IOException {
		StringBuilder originalHtml = new StringBuilder();
		originalHtml.append(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");

		Element html = queryRaw(vorto, language);
		if (html == null) {
			return null;
		}
		originalHtml.append(html.outerHtml());

		originalHtml.append("</body></html>");

		String resultHtml = CssInliner.inlineCss(originalHtml.toString(), getCss());

		return List.of(new VortaroSourceResult(resultHtml));
	}

	private Element queryRaw(String vorto, Language language) throws IOException {
		if (language != Language.ESPERANTO)
			return null;
		if (!Language.isEsperantoWord(vorto))
			return null;

		HttpGet get;
		try {
			String url = "https://vortaro.net/py/serchi.py?m=%7B%22s%22%3A%22" + vorto
					+ "%22%2C%22artikoloj_jamaj%22%3A0%2C%22kap%22%3A1%2C%22der%22%3A1%2C%22tek%22%3A0%2C%22uskle%22%3A0%7D";
			URIBuilder builder = new URIBuilder(url);
			builder.setCharset(Charset.forName("UTF-8"));
			get = new HttpGet(builder.build());
		} catch (URISyntaxException e) {
			// impossible
			throw new RuntimeException(e);
		}

		HttpResponse response = http.execute(get, httpContext);
		String rawJson = EntityUtils.toString(response.getEntity());

		PivResponse res = gson.fromJson(rawJson, PivResponse.class);
		if (res.getVortoj().isEmpty()) {
			// 没有查到结果
			return null;
		}
		Integer vortoId = res.getVortoj().get(0).get(0); // 返回的精确词id

		Element partHtml = parseVortoPart(res.getPivdok(), vortoId);
		return partHtml;
	}

	/**
	 * 从html中解析出指定单词的部分。
	 * 
	 * @param html
	 * @param vortoId
	 */
	private Element parseVortoPart(String html, Integer vortoId) {
		Document root = Jsoup.parse(html);

		// 找到 k+vortoId 的父节点则为原型词， d+vortoId 的父节点则为派生词
		Element vortoElement = root.getElementById("k" + vortoId);
		if (vortoElement == null) {
			vortoElement = root.getElementById("d" + vortoId);
		}
		if (vortoElement == null) {
			throw new RuntimeException("Cannot find vorto element: " + vortoId);
		}
		vortoElement = vortoElement.parent();

		// 去掉所有派生词节点
		vortoElement.children().forEach(child -> {
			if (child.hasClass("derivajho")) {
				child.remove();
			}
		});

		return vortoElement;
	}

	private String getCss() throws IOException {
		return css == null ? refreshCss() : css;
	}

	private synchronized String refreshCss() throws IOException {
		if (css != null)
			return css;

		HttpGet get;
		try {
			URIBuilder builder = new URIBuilder("https://vortaro.net/stilo/css/piv.css");
			get = new HttpGet(builder.build());
		} catch (URISyntaxException e) {
			// impossible
			throw new RuntimeException(e);
		}

		HttpResponse response = http.execute(get, httpContext);
		css = EntityUtils.toString(response.getEntity());
		return css;
	}

}
