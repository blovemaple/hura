package com.github.blovemaple.hura.source;

import static com.github.blovemaple.hura.util.MyUtils.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.source.ChenQueryResult.ListItem;
import com.github.blovemaple.hura.util.Conf;
import com.google.gson.Gson;

/**
 * 陈在伟老师提供的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChenVortaro implements VortaroSource {

	private Gson gson = new Gson();

	private String key;
	{
		try {
			key = Conf.str("private", "chenvortaro.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String name() {
		return "世汉词典";
	}

	@Override
	public List<VortaroSourceResult> query(String vorto) throws IOException {
		if ("aaaaa".equals(vorto)) {
			// 方便调试
			return Collections.singletonList(
					new VortaroSourceResult(String.join("\n", Files.readAllLines(Paths.get("/home/blove/tmp/test")))));
		}
		try {
			ChenQueryResult queryResult = queryResult(vorto);
			if (queryResult.getStatus() == 0)
				// 接口失败
				return null;
			if (queryResult.getSum() == 0)
				// 查询无结果
				return null;

			if (hasChinese(vorto)) {
				// 输入是中文
				// 取所有在非括号内出现关键词的结果
				return queryResult.getList().stream()
						// 清理一下
						.peek(this::cleanSignifo)
						// 在非括号内出现关键词的结果
						.filter(item -> isValidSignifo(item.getSignifo(), vorto))
						.map(item -> new VortaroSourceResult(item.getRadiko(), item.getSignifo()))
						.collect(Collectors.toList());
			} else {
				// 输入是世界语
				// 只取完全匹配的单词的释义
				List<String> results = queryResult.getList().stream()
						.filter(item -> item.getRadiko().equalsIgnoreCase(vorto)).map(ListItem::getSignifo)
						.collect(Collectors.toList());
				if (results.isEmpty())
					return null;
				else if (results.size() == 1)
					return Collections.singletonList(new VortaroSourceResult(results.get(0)));
				else
					return results.stream().map(VortaroSourceResult::new).collect(Collectors.toList());
			}
		} catch (URISyntaxException e) {
			// 不可能
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private ChenQueryResult queryResult(String vorto) throws URISyntaxException, ParseException, IOException,
			KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return gson.fromJson(queryRaw(vorto), ChenQueryResult.class);
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

	private void cleanSignifo(ChenQueryResult.ListItem item) {
		item.setSignifo(item.getSignifo().replaceAll("\\/.+", "").replaceAll("参 考 资 料.*", "").trim());
	}

	private boolean isValidSignifo(String signifo, String vorto) {
		String pureSignifo = signifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		while (!signifo.equals(pureSignifo)) {
			signifo = pureSignifo;
			pureSignifo = pureSignifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		}
		return pureSignifo.contains(vorto);
	}

	public static void main(String[] args) {
		String signifo = "[火车]哈哈[ef[78787]joei]";
		String pureSignifo = signifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		while (!signifo.equals(pureSignifo)) {
			signifo = pureSignifo;
			pureSignifo = pureSignifo.replaceAll("\\[[^\\[\\]]+\\]", "");
		}
		System.out.println(pureSignifo);
	}

}
