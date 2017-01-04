package com.github.blovemaple.hura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.xmlutil.XmlUtils;

/**
 * 处理微信请求的servlet。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@WebServlet("/request")
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 响应长度限制
	 */
	private static final int MAX_RESULT_LENGTH = 2047;
	/**
	 * 查询超时，单位秒
	 */
	private static final int QUERY_TIMEOUT = 4;

	private Vortaro vortaro = new Vortaro();

	public RequestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Message message;
		try {
			message = parseMessage(request);
			if (message == null) {
				noResponse(response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			noResponse(response);
			return;
		}

		try {
			if ("event".equals(message.getMsgType())) {
				if ("subscribe".equals(message.getEvent())) {
					writeResponse("Bonvenon!\n"
							+ "欢迎使用Hura！Hura是一个世界语汉语双向词典/翻译工具，向我发送世界语或汉语即可得到解释或翻译。目前Hura的词典来源为陈在伟老师提供的词典，以及lernu.net词典作为辅助；若两个词典均查不到，则会使用谷歌翻译。\n"
							+ "目前Hura还不成熟，如有改进建议请直接留言。希望Hura能帮到你。 :)", message, response);
					return;
				} else
					noResponse(response);
				return;
			} else if ("text".equals(message.getMsgType())) {
				String reqContent = message.getContent();
				if (reqContent == null || reqContent.isEmpty()) {
					noResponse(response);
					return;
				}
				List<VortaroResult> results = vortaro.query(reqContent, QUERY_TIMEOUT);
				if (results != null && !results.isEmpty())
					writeResponse(resultsToString(results), message, response);
				else
					writeResponse("Mi ne komprenas ĉi tiun tekston.\n抱歉，我看不懂你输入的内容。 :(", message, response);
				return;
			} else {
				writeResponse("Mi nur komprenas tekstojn.\n我只认识文字消息哦，给我发文字吧。 :)", message, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			writeResponse("Mi bedaŭras, sistemeraro estas okazinta.\n抱歉，我暂时出了点问题。请等会儿再来吧。 :(", message, response);
			return;
		}
	}

	private Message parseMessage(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");

		StringWriter strWriter = new StringWriter();
		try (BufferedReader reader = request.getReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				strWriter.write(line);
			}
		}
		String str = strWriter.toString();
		System.out.println("[REQUEST]" + str);
		if (str.isEmpty()) {
			return null;
		}
		return XmlUtils.fromXml(strWriter.toString(), Message.class);
	}

	private static String resultsToString(List<VortaroResult> results) {
		int maxSingleSize = MAX_RESULT_LENGTH / results.size();
		StringBuilder str = new StringBuilder();
		results.forEach(result -> str.append(resultToString(result, maxSingleSize)).append("\n\n"));
		return str.deleteCharAt(str.length() - 1).deleteCharAt(str.length() - 1).toString();
	}

	private static String resultToString(VortaroResult result, int maxSize) {
		try {
			StringBuilder str = new StringBuilder();
			VortaroSource source = result.getSource();
			str.append("【").append(source.name()).append("】").append("\n");
			if (source.tip() != null)
				str.append("（").append(source.tip()).append("）").append("\n");

			int size = str.toString().getBytes("utf-8").length;

			for (VortaroSourceResult sourceResult : result.getResults()) {
				StringBuilder singleResultStr = new StringBuilder();
				if (sourceResult.getTitle() != null)
					singleResultStr.append("◆").append(sourceResult.getTitle()).append("\n");
				singleResultStr.append(sourceResult.getContent()).append("\n");
				size += singleResultStr.toString().getBytes("utf-8").length;
				if (size > maxSize)
					break;
				str.append(singleResultStr);
			}
			return str.deleteCharAt(str.length() - 1).toString();
		} catch (UnsupportedEncodingException e) {
			// 不可能
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(resultsToString(new Vortaro().query("吃", QUERY_TIMEOUT)));
	}

	private void writeResponse(String resContent, Message reqMessage, HttpServletResponse response) throws IOException {
		Message resMessage = new Message();
		resMessage.setFromUserName(reqMessage.getToUserName());
		resMessage.setToUserName(reqMessage.getFromUserName());
		resMessage.setCreateTime(reqMessage.getCreateTime());
		resMessage.setMsgType("text");
		resMessage.setContent(resContent);

		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		String resString = XmlUtils.toXml(resMessage);
		writer.print(resString);
		writer.flush();

		System.out.println("[RESPONSE]" + resString);
	}

	private void noResponse(HttpServletResponse response) {
		System.out.println("[NO_RESPONSE]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
