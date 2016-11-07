package com.github.blovemaple.hura.vortaro;

import static com.github.blovemaple.hura.util.MyUtils.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

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
			key = Conf.str("private", "chenvortaro.key");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String query(String vorto) throws IOException {
		if ("aaaaa".equals(vorto)) {
			// 方便调试
			return String.join("\n", Files.readAllLines(Paths.get("/home/blove/tmp/test")));
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
				// 取所有结果，但根据微信要求不能达到2048字节
				StringBuilder result = new StringBuilder();
				int byteCount = 0;
				for (ListItem item : queryResult.getList()) {
					String itemStr = '【' + item.getRadiko() + '】' + '\n' + item.getSignifo() + '\n' + '\n';
					int itemByteCount = itemStr.toString().getBytes("utf-8").length;
					if (byteCount + itemByteCount < 2048) {
						result.append(itemStr);
						byteCount += itemByteCount;
					}
				}
				result.delete(result.length() - 2, result.length());
				return result.toString();
			} else {
				// 输入是世界语
				// 只取完全匹配的单词的释义
				List<String> results = queryResult.getList().stream()
						.filter(item -> item.getRadiko().equalsIgnoreCase(vorto)).map(ListItem::getSignifo)
						.collect(Collectors.toList());
				if (results.isEmpty())
					return null;
				else if (results.size() == 1)
					return results.get(0);
				else
					return String.join("\n", results);
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
		System.out.println(v.query("jaro"));
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

}
