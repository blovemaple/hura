package com.github.blovemaple.hura.source;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

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

import com.github.blovemaple.hura.util.CssInliner;
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
	private String css;

	@Override
	public String name() {
		return "PIV(vortaro.net)";
	}

	@Override
	public boolean hasDetail() {
		return true;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("CONTENT: " + new Piv().query("lernado"));
		// Files.writeString(Paths.get("E:\\out.html"), new
		// Piv().queryDetail("vortaro").get(0).getContent());
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		StringBuilder resultString = new StringBuilder();
		contentElements(vorto, language).forEach(e -> resultNodeToString(e, resultString, 0));
		if (resultString.length() == 0)
			return null;
		return List.of(new VortaroSourceResult(resultString.toString()));
	}

	@Override
	public List<VortaroSourceResult> queryDetail(String vorto, Language language) throws IOException {
		StringBuilder originalHtml = new StringBuilder();
		originalHtml.append(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");

		// contentElements(vorto, language).forEach(e -> e.html(originalHtml));
		contentElements(vorto, language).forEach(e -> originalHtml.append(e.outerHtml()));
		if (originalHtml.length() == 0)
			return null;

		originalHtml.append("</body></html>");

		String resultHtml = CssInliner.inlineCss(originalHtml.toString(), getCss());

		return List.of(new VortaroSourceResult(resultHtml));
	}

	/**
	 * 查询并获取指定单词释义的html节点。<br>
	 * 因为查出来的html中，查的那个单词有可能属于派生单词（见下注释），所以这时候需要只保留它自己的节点。
	 */
	private Stream<Element> contentElements(String vorto, Language language) throws IOException {
		if (language != Language.ESPERANTO)
			return Stream.empty();
		if (!Language.isEsperantoWord(vorto))
			return Stream.empty();

		String rawHtml = queryRaw(vorto);
		if (rawHtml == null)
			return Stream.empty();

		Document root = Jsoup.parse(rawHtml);

		// 查找id=ow的节点中的单词原型
		String targetWord;
		Element OwElement = root.getElementById("ow");
		if (OwElement != null)
			targetWord = OwElement.attr("data-origin");
		else
			targetWord = vorto;

		// 找到所有"article"节点
		Elements articleElements = root.getElementsByClass("article");

		// article的节点结构是（这里写的都是class）：
		// article
		// -- article_content_xxxx
		// ---- <单词>（dfn节点，第一个单词，不一定是查的那一个，有可能在派生单词里）
		// ---- signifonuanco*
		// ---- derivo*
		// ------ <单词>（dfn节点，派生单词）
		// ------ signifonuanco*

		// 首先按targetWord找到dfn节点，
		// 如果是第一个单词则取整个article节点，
		// 如果是派生单词，则只保留article、article_content_xxxx、对应的derivo节点

		return articleElements.stream().map(articleElement -> {
			// 先按照targetWord精确查找dfn节点
			Elements targetDfnElements = articleElement.getElementsByClass(targetWord);
			if (!targetDfnElements.isEmpty()) {
				// 找到了精确的dfn节点，判断是不是派生单词（在derivo节点下）
				Element dfnElement = targetDfnElements.first();
				if (dfnElement.parent().hasClass("derivo")) {
					// 是派生单词，去掉对应的derivo节点的所有sibling节点
					dfnElement.parent().siblingNodes().forEach(Node::remove);
				}
			}
			return articleElement;
		});
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
		String rawHtml = EntityUtils.toString(response.getEntity());

		Document root = Jsoup.parse(rawHtml);

		// 找到所有"article"节点
		Elements articleElements = root.getElementsByClass("article");
		if (articleElements.isEmpty()) {
			Elements errorsElements = root.getElementsByClass("errors");
			if (errorsElements.html().contains("Okazis iu eraro")) {
				// token非法，刷新token后重试
				refreshToken();
				return queryRaw(vorto, currentRetry + 1);
			} else if (errorsElements.html().contains("ne estis sukcesa")) {
				// 没有查询结果
				return null;
			} else {
				// 未知错误，打印日志，返回没有查询结果
				logger.error("Query vortaro.net error: " + root.html());
				return null;
			}
		}

		return rawHtml;
	}

	/**
	 * @param node
	 * @param resultString
	 * @param difinogrupoDepth
	 *            有时候单词本身的解释里会有derivo节点，这种情况下貌似都会在difinogrupo节点下。<br>
	 *            因此用这个变量表示目前的difinogrupo节点层级，如果>1说明在difinogrupo节点下，就不过滤deviro。
	 */
	private void resultNodeToString(Node node, StringBuilder resultString, int difinogrupoDepth) {
		if (node instanceof Element) {
			// div节点前换行
			if (resultString.length() > 0 && ((Element) node).tagName().equals("div"))
				resultString.append("\n");

			// 循环递归子节点
			for (Node child : node.childNodes()) {
				// 排除有siblings的衍生单词节点（没有siblings说明是被选出来的目标节点）
				if (difinogrupoDepth == 0 && child instanceof Element && ((Element) child).hasClass("derivo")) {
					if (!((Element) child).siblingElements().isEmpty())
						continue;
				}

				// 排除上标节点
				if (child instanceof Element && ((Element) child).tagName().equals("sup"))
					continue;

				// 排除Vortoreferenco之后的部分
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
				int childDifinogrupoDepth = difinogrupoDepth;
				if (child instanceof Element && ((Element) child).hasClass("difinogrupo")) {
					childDifinogrupoDepth++;
				}
				resultNodeToString(child, resultString, childDifinogrupoDepth);

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

	private String getCss() throws IOException {
		return css == null ? refreshCss() : css;
	}

	private synchronized String refreshCss() throws IOException {
		if (css != null)
			return css;

		HttpGet get;
		try {
			URIBuilder builder = new URIBuilder("http://vortaro.net/css/common.css");
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
