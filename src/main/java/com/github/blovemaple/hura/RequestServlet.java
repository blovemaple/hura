package com.github.blovemaple.hura;

import static com.github.blovemaple.hura.ResponseStatus.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pmw.tinylog.Logger;

import com.github.blovemaple.hura.ocr.GoogleOcr;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.util.Conf;
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
	private static final int QUERY_TIMEOUT = 4000;

	private Vortaro vortaro = new Vortaro();
	private GoogleOcr ocr = new GoogleOcr();

	private String myUserName;
	{
		try {
			myUserName = Conf.str("private", "myusername");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RequestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Message message;
		try {
			message = parseMessage(request);
			if (message == null) {
				noResponse(null, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			noResponse(null, response);
			return;
		}

		long startTime = System.currentTimeMillis();

		try {
			switch (message.getMsgType()) {
			case "event":
				if ("subscribe".equals(message.getEvent())) {
					writeResponse(SUCC, startTime, "Bonvenon!\n"
							+ "欢迎使用Hura！Hura是一个世界语汉语双向词典/翻译工具，向我（Hura公众号）发送世界语或汉语即可得到解释或翻译。目前Hura的词典来源为陈在伟老师提供的世汉词典，以及lernu.net词典作为辅助；若两个词典均查不到，则会使用谷歌翻译。\n"
							+ "目前Hura还不成熟，如有改进建议请发送邮件给作者：blovemaple2010@gmail.com。\n"
							+ "Hura后台服务代码开源，如果你是程序猿/媛朋友，可在Github上搜索“hura”。\n" + "希望Hura能帮到你。 :)", message, response);
					return;
				} else
					noResponse(message, response);
				return;
			case "text":
				String reqContent = message.getContent();
				if (reqContent == null || reqContent.isEmpty()) {
					noResponse(message, response);
					return;
				}
				List<VortaroResult> results = vortaro.query(reqContent, QUERY_TIMEOUT);
				if (results != null && !results.isEmpty())
					writeResponse(SUCC, startTime, resultsToString(results), message, response);
				else
					writeResponse(FAIL, startTime, "Mi ne komprenas ĉi tiun tekston.\n抱歉，我看不懂你输入的内容。 :(", message,
							response);
				return;
			case "image":
				if (myUserName.equals(message.getFromUserName())) {
					String picUrl = message.getPicUrl();
					if (picUrl == null || picUrl.isEmpty()) {
						noResponse(message, response);
						return;
					}
					String text;
					try {
						text = ocr.recognize(picUrl);
					} catch (IOException e) {
						e.printStackTrace();
						writeResponse(SERR, startTime,
								"Mi bedaŭras, sistemeraro estas okazinta.\n抱歉，图片识别暂时出了点问题。请发文字吧。 :(", message,
								response);
						return;
					}
					if (text == null || text.isEmpty()) {
						writeResponse(FAIL, startTime,
								"Mi bedaŭras, estas ne teksto en ĉi tiu bildo.\n抱歉，我识别不出图片里的文字，或是您的图片里没有文字。请发带世界语或汉语文字的、清晰度尽量高的图片。 :)",
								message, response);
						return;
					}

					long costNow = System.currentTimeMillis() - startTime;
					List<VortaroResult> vortaroResults = vortaro.query(text, (int) (QUERY_TIMEOUT - costNow));
					String vortaroResultsString;
					if (vortaroResults != null && !vortaroResults.isEmpty())
						vortaroResultsString = resultsToString(vortaroResults);
					else
						vortaroResultsString = "【Hura】\nMi ne komprenas ĉi tiun tekston.\n抱歉，我看不懂这些文字，无法提供解释或翻译。 :(";
					writeResponse(SUCC, startTime, wrapOcrTextToResponse(text) + vortaroResultsString + ocrTip(),
							message, response);
					return;
				}
			default:
				// writeResponse("Mi nur komprenas tekstojn kaj
				// bildojn.\nHura目前只认识文字和图片消息哦，给我发文字，或者试试发图片吧。 :)", message, response);
				writeResponse(NOSU, startTime,
						"Mi nur komprenas tekstojn.\nHura目前只认识文字消息哦，给我发文字吧。（图片文字识别功能即将开启，敬请期待） :)", message, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResponse(SERR, startTime, "Mi bedaŭras, sistemeraro estas okazinta.\n抱歉，我暂时出了点问题。请等会儿再来吧。 :(", message,
					response);
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
		if (str.isEmpty()) {
			return null;
		}
		return XmlUtils.fromXml(strWriter.toString(), Message.class);
	}

	private String wrapOcrTextToResponse(String text) {
		return "【识别文字】\n" + text + "\n";
	}

	private String ocrTip() {
		return "\n\n（图片识别文字准确率较低，请尽量使用清晰度高的图片，并慎重参考识别和翻译结果！）";
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
		System.out.println("abc\ndef\r\nghi jkl\tmn".replaceAll("[\\r\\n\\s]", " "));
	}

	private void writeResponse(ResponseStatus status, long startTime, String resContent, Message reqMessage,
			HttpServletResponse response) throws IOException {
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

		log(status, startTime, reqMessage, resContent);
	}

	private void noResponse(Message reqMessage, HttpServletResponse response) {
		log(NULL, null, reqMessage, null);
	}

	private void log(ResponseStatus status, Long startTime, Message reqMessage, String resContent) {
		// status cost fromuser type req res
		Logger.info(() -> {
			long cost = startTime == null ? -1 : System.currentTimeMillis() - startTime;
			List<String> infos = new ArrayList<>();

			infos.add(status.name());
			infos.add(Long.toString(cost));
			infos.add(reqMessage == null ? "null" : reqMessage.getFromUserName());
			infos.add(reqMessage == null ? "null" : reqMessage.getMsgType());
			String reqContent;
			switch (reqMessage == null ? "null" : reqMessage.getMsgType()) {
			case "event":
				reqContent = reqMessage.getEvent();
				break;
			case "text":
				reqContent = reqMessage.getContent();
				break;
			case "image":
				reqContent = reqMessage.getPicUrl();
				break;
			default:
				reqContent = "null";
			}
			infos.add(reqContent.replaceAll("[\\r\\n\\s]", " "));
			infos.add(resContent == null ? "null" : resContent.replaceAll("[\\r\\n\\s]", " "));

			return String.join("\t", infos);
		});
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
