package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.blovemaple.hura.util.Language;

/**
 * Vortaro.net
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Piv implements VortaroSource {
	private static final Logger logger = LoggerFactory.getLogger(Piv.class);

	HttpClientContext httpContext = HttpClientContext.create();
	HttpClient http = HttpClients.custom()
			.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
	private String token;

	@Override
	public String name() {
		return "PIV(vortaro.net)";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		if (language != Language.ESPERANTO)
			return null;
		if (!Language.isEsperantoWord(vorto))
			return null;

		// TODO
		return null;

	}

	public static void main(String[] args) throws IOException {
		System.out.println("CONTENT: " + new Piv().queryRaw("iu"));
	}

	public String queryRaw(String vorto) throws IOException {
		return queryRaw(vorto, 0);
	}

	private String queryRaw(String vorto, int currentRetry) throws IOException {
		if (currentRetry >= 5) {
			// 限制重试次数
			logger.info("Access vortaro.net retry exceeds limit.");
			return null;
		}

		HttpGet get;
		try {
			String url = "http://vortaro.net/ajax/articles/" + URLEncoder.encode(vorto, Charset.forName("UTF-8")) + "/"
					+ getToken();
			URIBuilder builder = new URIBuilder(url);
			builder.setCharset(Charset.forName("UTF-8"));
			get = new HttpGet(builder.build());
		} catch (URISyntaxException e) {
			// impossible
			throw new RuntimeException(e);
		}

		HttpResponse response = http.execute(get, httpContext);
		String responseStr = EntityUtils.toString(response.getEntity());

		Document root = Jsoup.parse(responseStr);
		Elements articleElements = root.getElementsByClass("article");
		if (articleElements.isEmpty()) {
			Elements errorsElements = root.getElementsByClass("errors");
			if (errorsElements.html().contains("Okazis iu eraro")) {
				// token非法，刷新token后重试
				refreshToken();
				queryRaw(vorto, currentRetry + 1);
			} else if (errorsElements.html().contains("ne estis sukcesa")) {
				// 没有查询结果
				return null;
			} else {
				// 未知错误，打印日志，返回没有查询结果
				logger.error("Query vortaro.net error: " + root.html());
				return null;
			}
		}

		Element articleElement = articleElements.get(0).child(0);

		// 先按照vorto精确查找dfn节点
		Elements targetElements = articleElement.getElementsByClass(vorto);
		Element contentElement;
		if (!targetElements.isEmpty()) {
			// 找到了精确的dfn节点，将父节点作为内容节点
			contentElement = targetElements.get(0).parent();
		} else {
			// 没有找到精确的dfn节点，以第一个单词（非derivo单词）的节点作为内容节点
			contentElement = articleElement.child(0);
		}

		StringBuilder resultString = new StringBuilder();
		convertResult(contentElement, resultString);
		return resultString.toString();
	}

	private void convertResult(Node node, StringBuilder resultString) {
		if (node instanceof Element) {
			// div节点前换行
			if (resultString.length() > 0 && ((Element) node).tagName().equals("div"))
				resultString.append("\n");

			// 循环递归子节点
			for (Node child : node.childNodes()) {
				// 排除衍生单词节点
				if (child instanceof Element && ((Element) child).hasClass("derivo"))
					continue;

				// 排除上标节点
				if (child instanceof Element && ((Element) child).tagName().equals("sup"))
					continue;

				// 排除Vortoreferenco之后的部分
				if (child instanceof Element && ((Element) child).hasClass("Vortoreferenco"))
					break;

				// 排除斜体之后的部分（斜体表示详细解释或者举例）
				if (child instanceof Element && ((Element) child).tagName().equals("i")) {
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
				convertResult(child, resultString);

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

	private String getToken() throws IOException {
		return token == null ? refreshToken() : token;
	}

	private synchronized String refreshToken() throws IOException {
		token = null;
		synchronized (this) {
			if (token != null)
				return token;

			logger.info("Refreshing token...");

			HttpGet get;
			try {
				URIBuilder builder = new URIBuilder("http://vortaro.net/");
				get = new HttpGet(builder.build());
			} catch (URISyntaxException e) {
				// impossible
				throw new RuntimeException(e);
			}

			HttpResponse response = http.execute(get, httpContext);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new IOException("Access vortaro.net failed, status " + response.getStatusLine().getStatusCode());
			List<Cookie> cookies = httpContext.getCookieStore().getCookies();
			Cookie picTokenCookie = cookies.stream().filter(cookie -> cookie.getName().equals("piv_token")).findFirst()
					.orElseThrow(() -> new IOException("Access vortaro.net response no piv_token cookie."));
			this.token = picTokenCookie.getValue();

			logger.info("Refresh token success, token=" + this.token);
			return token;
		}
	}

}
