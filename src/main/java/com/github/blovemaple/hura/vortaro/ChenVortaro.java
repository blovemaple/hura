package com.github.blovemaple.hura.vortaro;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.blovemaple.hura.util.Conf;
import com.github.blovemaple.hura.vortaro.ChenQueryResult.ListItem;
import com.google.gson.Gson;

/**
 * 陈在伟老师提供的词典。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChenVortaro implements Vortaro {

	private Gson gson = new Gson();

	private String key;
	{
		try {
			key = Conf.str("prvate", "chenvortaro.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String query(String vorto) throws IOException {
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
				// 取所有结果
				StringBuilder result = new StringBuilder();
				queryResult.getList().forEach(item -> {
					result.append('【').append(item.getRadiko()).append('】').append('\n');
					result.append(item.getSignifo()).append('\n');
					result.append('\n');
				});
				result.delete(result.length() - 2, result.length());
				return result.toString();
			} else {
				// 输入是世界语
				// 只取完全匹配的一个单词的释义
				return queryResult.getList().stream().filter(item -> item.getRadiko().equalsIgnoreCase(vorto)).findAny()
						.map(ListItem::getSignifo).orElse(null);
			}
		} catch (URISyntaxException e) {
			// 不可能
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		ChenVortaro v = new ChenVortaro();
		System.out.println(v.query("恐怖"));
	}

	private static boolean hasChinese(String str) {
		return str.chars().anyMatch(c -> isChinese((char) c));
	}

	private static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}

	private ChenQueryResult queryResult(String vorto) throws URISyntaxException, ParseException, IOException,
			KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return gson.fromJson(queryRaw(vorto), ChenQueryResult.class);
	}

	private String queryRaw(String vorto) throws ParseException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder("http://120.76.43.226/vorto/search.php");
		builder.setParameter("key", key) //
				.setParameter("str", vorto);
		builder.setCharset(Charset.forName("UTF-8"));
		HttpGet get = new HttpGet(builder.build());
		HttpClient http = HttpClients.createSystem();
		HttpResponse response = http.execute(get);
		return EntityUtils.toString(response.getEntity());
	}

}
