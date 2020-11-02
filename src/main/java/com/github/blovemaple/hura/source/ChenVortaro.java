package com.github.blovemaple.hura.source;

import static com.github.blovemaple.hura.util.MyUtils.isChinese;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.mybatis.dynamic.sql.SqlBuilder;

import com.github.blovemaple.hura.SpringContext;
import com.github.blovemaple.hura.dal.Vorto5000;
import com.github.blovemaple.hura.dal.Vorto5000DynamicSqlSupport;
import com.github.blovemaple.hura.dal.Vorto5000Mapper;
import com.github.blovemaple.hura.source.ChenQueryResult.ListItem;
import com.github.blovemaple.hura.util.Conf;
import com.github.blovemaple.hura.util.Language;
import com.google.gson.Gson;

/**
 * 陈在伟老师提供的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChenVortaro implements VortaroSource {

	private boolean goodOnly = true;

	private Gson gson = new Gson();
	
	private String key;
	{
		try {
			key = Conf.str("private", "chenvortaro.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ChenVortaro() {
	}

	public ChenVortaro(boolean goodOnly) {
		this.goodOnly = goodOnly;
	}

	@Override
	public String name() {
		return "世汉词典";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto, Language language) throws IOException {
		try {
			List<ChenQueryResult.ListItem> queryResult = queryResultFromDB(vorto, language);

			switch (language) {
			case CHINESE:
				// 输入是中文
				List<ListItem> finalItems;

				// 取有效结果
				List<ListItem> validItems = queryResult.stream()
						// 清理一下
						.peek(this::cleanSignifo)
						// 过滤出有效结果
						.filter(item -> isValidSignifo(item.getSignifo(), vorto)).collect(Collectors.toList());

				if (goodOnly) {
					// 从有效结果中取好的结果
					List<ListItem> goodItems = validItems.stream()
							// 过滤出好的结果
							.filter(item -> isGoodSignifo(item.getSignifo(), vorto)) //
							.collect(Collectors.toList());
					// 如果有好的结果，只取好的；否则取所有有效的
					finalItems = goodItems.isEmpty() ? validItems : goodItems;
				} else {
					finalItems = validItems;
				}

				return finalItems.stream() //
						.map(item -> new VortaroSourceResult(item.getRadiko(), item.getSignifo()))
						.collect(Collectors.toList());

			case ESPERANTO:
				// 输入是世界语
				// 只取完全匹配的单词的释义
				List<String> results = queryResult.stream()
						.filter(item -> item.getRadiko().equalsIgnoreCase(vorto)).map(ListItem::getSignifo)
						.collect(Collectors.toList());
				if (results.isEmpty())
					return null;
				else if (results.size() == 1)
					return Collections.singletonList(new VortaroSourceResult(vorto, results.get(0)));
				else
					return results.stream().map(content -> new VortaroSourceResult(vorto, content))
							.collect(Collectors.toList());

			default:
				return null;
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unused")
	private List<ChenQueryResult.ListItem> queryResult(String vorto) throws URISyntaxException, ParseException,
			IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		ChenQueryResult queryResult = gson.fromJson(queryRaw(vorto), ChenQueryResult.class);
		if (queryResult.getStatus() == 0)
			// 接口失败
			return null;
		if (queryResult.getSum() == 0)
			// 查询无结果
			return null;
		return queryResult.getList();
	}

	private String queryRaw(String vorto) throws ParseException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder("http://120.76.43.226/vorto/wxapi.php");
		builder.setParameter("key", key) //
				.setParameter("str", vorto);
		builder.setCharset(Charset.forName("UTF-8"));
		HttpGet get = new HttpGet(builder.build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}

	public List<ChenQueryResult.ListItem> queryResultFromDB(String vorto, Language language) {
		return queryResultFromDB(vorto, language, null, false);
	}

	public List<ChenQueryResult.ListItem> queryResultFromDB(String vorto, Language language, Integer limit,
			boolean eoAsPrefix) {
		Vorto5000Mapper vorto5000Mapper = SpringContext.getBean(Vorto5000Mapper.class);

		List<Vorto5000> dbResults;
		switch (language) {
		case CHINESE:
			dbResults = vorto5000Mapper
					.select(c -> c.where(Vorto5000DynamicSqlSupport.signifo, SqlBuilder.isLike(() -> "%" + vorto + "%"))
							.limit(limit != null ? limit : 100));
			break;
		case ESPERANTO:
			if (eoAsPrefix) {
				dbResults = vorto5000Mapper
						.select(c -> c.where(Vorto5000DynamicSqlSupport.radiko, SqlBuilder.isLike(() -> vorto + "%"))
								.limit(limit != null ? limit : 100));
			} else {
				dbResults = vorto5000Mapper
						.select(c -> c.where(Vorto5000DynamicSqlSupport.radiko, SqlBuilder.isEqualTo(vorto)));
			}
			break;
		default:
			return null;
		}

		List<ChenQueryResult.ListItem> result = dbResults.stream()
				.map(dbResult -> new ChenQueryResult.ListItem(dbResult.getRadiko(), dbResult.getSignifo()))
				.collect(Collectors.toList());
		return result;
	}

	private void cleanSignifo(ChenQueryResult.ListItem item) {
		item.setSignifo(item.getSignifo().replaceAll("\\/.+", "").replaceAll("参 考 资 料.*", "").trim());
	}

	/**
	 * 除了“[...]”以外的部分出现查询词，才是有效的含义。
	 */
	private boolean isValidSignifo(String signifo, String vorto) {
		String pureSignifo = getPureSignifo(signifo);
		return pureSignifo.contains(vorto);
	}

	/**
	 * 符合此条件是好的含义：<br>
	 * 除了“[...]”以外的部分按照非中文字符分隔，单独出现查询词，或者单独出现查询词后加“的”或“地”。
	 */
	private boolean isGoodSignifo(String signifo, String vorto) {
		List<String> words = splitWords(getPureSignifo(signifo));
		List<String> targetWords = Arrays.asList(vorto, vorto + "的", vorto + "地");
		return !Collections.disjoint(words, targetWords);
	}

	private String getPureSignifo(String signifo) {
		String pureSignifo = signifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		while (!signifo.equals(pureSignifo)) {
			signifo = pureSignifo;
			pureSignifo = pureSignifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		}
		return pureSignifo;
	}

	private List<String> splitWords(String signifo) {
		if (StringUtils.isBlank(signifo))
			return Collections.emptyList();

		List<String> words = new ArrayList<>();
		StringBuilder crtWord = new StringBuilder();
		signifo.chars().forEach(c -> {
			if (isChinese((char) c)) {
				crtWord.append((char) c);
			} else {
				if (StringUtils.isNotBlank(crtWord)) {
					words.add(crtWord.toString());
				}
				crtWord.setLength(0);
			}
		});
		if (StringUtils.isNotBlank(crtWord)) {
			words.add(crtWord.toString());
		}
		return words;
	}

	public static void main(String[] args) throws IOException {
		String vorto = "重要";

		ChenVortaro vortaro = new ChenVortaro();
		ChenVortaro validVortaro = new ChenVortaro(false);
		vortaro.query(vorto).forEach(System.out::println);
		System.out.println();
		validVortaro.query(vorto).forEach(System.out::println);
	}

}
