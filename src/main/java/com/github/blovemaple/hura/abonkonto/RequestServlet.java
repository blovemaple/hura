package com.github.blovemaple.hura.abonkonto;

import static com.github.blovemaple.hura.abonkonto.ResponseStatus.FAIL;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.ILGL;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.NOSU;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.NULL;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.SERR;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.SUCC;
import static com.github.blovemaple.hura.abonkonto.ResponseStatus.TOKN;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.blovemaple.hura.dal.AbonkontoLog;
import com.github.blovemaple.hura.dal.AbonkontoLogMapper;
import com.github.blovemaple.hura.ocr.GoogleOcr;
import com.github.blovemaple.hura.ocr.OcrResult;
import com.github.blovemaple.hura.ocr.OcrResultType;
import com.github.blovemaple.hura.source.GoogleTranslate2;
import com.github.blovemaple.hura.source.VortaroSource;
import com.github.blovemaple.hura.source.VortaroSourceResult;
import com.github.blovemaple.hura.util.Conf;
import com.github.blovemaple.hura.xmlutil.XmlUtils;
import com.google.common.hash.Hashing;

/**
 * 处理微信请求的servlet。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@WebServlet("/hura-abonkonto/request")
@Component
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 响应长度限制
	 */
	private static final int MAX_RESULT_LENGTH = 2047;
	/**
	 * 查询超时，单位毫秒
	 */
	private static final int QUERY_TIMEOUT = 4000;

	private Vortaro vortaro = new Vortaro();
	private GoogleOcr ocr = new GoogleOcr();
	private GoogleTranslate2 googleTranslate = new GoogleTranslate2();

	@Autowired
	private AbonkontoLogMapper logMapper;

	@SuppressWarnings("unused")
	private String myUserName;
	{
		try {
			myUserName = Conf.str("private", "myusername");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String wechatToken;
	{
		try {
			wechatToken = Conf.str("private", "wechat.token");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信请求中偶尔会以text类型发来这个消息（至少发送动图表情的时候是这样）
	 */
	private static final String UNSUPPORTED_MESSAGE_CONTENT = "【收到不支持的消息类型，暂无法显示】";

	public RequestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (echostr(request, response))
			return;

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

		if (!checkValid(request, response, message))
			return;

		long startTime = System.currentTimeMillis();

		try {
			switch (message.getMsgType()) {
			case "event":
				if ("subscribe".equals(message.getEvent())) {
					writeResponse(SUCC, startTime, "Bonvenon!\n"
							+ "欢迎使用Hura！Hura是一个世界语汉语双向词典/翻译工具，请点击右上角图标，在“相关小程序”中使用“Hura世界语助手”小程序。如果不想使用小程序，也可以直接向我（Hura公众号）发送文字或图片消息即可得到查询结果。\n"
							+ "目前Hura还不成熟，如有改进建议请发送邮件给作者：blovemaple2010@gmail.com。\n"
							+ "Hura后台服务代码开源，如果你是程序猿/媛朋友，可在Github上搜索“hura”。\n" + "希望Hura能帮到你。 :)", message, response);
					return;
				} else
					noResponse(message, response);
				return;
			case "text":
				String reqContent = message.getContent();
				if (reqContent == null || StringUtils.isBlank(reqContent)) {
					noResponse(message, response);
					return;
				}

				if (UNSUPPORTED_MESSAGE_CONTENT.equals(reqContent)) {
					writeResponse(NOSU, startTime, "抱歉，您发送的消息微信暂不支持。请发送文字或静态图片消息。 :)", message, response);
				}

				List<VortaroResult> results = vortaro.query(reqContent, null, QUERY_TIMEOUT);
				if (results != null && !results.isEmpty())
					writeResponse(SUCC, startTime, resultsToString(results), message, response);
				else
					writeResponse(FAIL, startTime, "Mi ne komprenas ĉi tiun tekston.\n抱歉，我看不懂你输入的内容。 :(", message,
							response);
				return;
			case "image":
				String picUrl = message.getPicUrl();
				if (picUrl == null || picUrl.isEmpty()) {
					noResponse(message, response);
					return;
				}
				OcrResult ocrResult;
				try {
					ocrResult = ocr.recognize(picUrl);
				} catch (IOException e) {
					e.printStackTrace();
					writeResponse(SERR, startTime, "Mi bedaŭras, sistemeraro estas okazinta.\n抱歉，图片识别暂时出了点问题。请发文字吧。 :(",
							message, response);
					return;
				}
				if (ocrResult == null) {
					writeResponse(FAIL, startTime,
							"Mi bedaŭras, mi ne povas rekoni ĉi tiun bildon.\n抱歉，我没能识别这张图片。请发带世界语或汉语文字的，或包含清晰物体的，清晰度尽量高的图片。 :)",
							message, response);
					return;
				}

				String content;
				switch (ocrResult.getType()) {
				case LABEL:
					content = googleTranslate.translate(ocrResult.getResult(), GoogleTranslate2.EN,
							GoogleTranslate2.EO);
					if (content == null) {
						content = googleTranslate.translate(ocrResult.getResult(), GoogleTranslate2.EN,
								GoogleTranslate2.ZH_CN);
					}
					break;
				case TEXT:
					content = ocrResult.getResult().trim();
					break;
				default:
					throw new RuntimeException("Unrecognized OCR result type: " + ocrResult.getType());
				}

				long costNow = System.currentTimeMillis() - startTime;
				List<VortaroResult> vortaroResults = vortaro.query(content, null, (int) (QUERY_TIMEOUT - costNow));
				String vortaroResultsString;
				if (vortaroResults != null && !vortaroResults.isEmpty())
					vortaroResultsString = resultsToString(vortaroResults);
				else
					vortaroResultsString = "【Hura】\nMi ne komprenas ĉi tiun tekston.\n抱歉，Hura无法提供以上内容的解释或翻译。 :(";
				writeResponse(SUCC, startTime,
						wrapOcrTextToResponse(content, ocrResult.getType()) + "\n\n" + vortaroResultsString, message,
						response);
				return;
			default:
				writeResponse(NOSU, startTime, "Mi nur komprenas tekstojn kaj bildojn.\nHura目前只认识文字和图片消息哦。 :)", message,
						response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResponse(SERR, startTime, "Mi bedaŭras, sistemeraro estas okazinta.\n抱歉，我暂时出了点问题。请等会儿再来吧。 :(", message,
					response);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	private boolean checkValid(HttpServletRequest request, HttpServletResponse response, Message message) {
		if (message != null && "0".equals(message.getFromUserName()))
			// 用于调试
			return true;

		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		if (timestamp == null || nonce == null || signature == null)
			return false;

		List<String> params = Stream.of(wechatToken, timestamp, nonce).sorted().collect(toList());
		String param = String.join("", params);
		String trueSignature = Hashing.sha1().hashString(param, Charset.forName("UTF-8")).toString();
		if (trueSignature.equals(signature))
			return true;
		else {
			illegalResponse(message, response);
			return false;
		}
	}

	private boolean echostr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String echostr = request.getParameter("echostr");
		if (echostr != null && checkValid(request, response, null)) {
			PrintWriter writer = new PrintWriter(response.getOutputStream(), false, Charset.forName("UTF-8"));
			writer.print(echostr);
			writer.flush();
			log(TOKN, null, null, null);
			return true;
		}
		return false;
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

	private String wrapOcrTextToResponse(String content, OcrResultType type) {
		switch (type) {
		case LABEL:
			return "【识别内容】\n（图片识别结果仅供参考，请尽量使用清晰度高的图片）\n图片中看起来没有文字，Hura认为图片的内容是：\n" + content;
		case TEXT:
			return "【识别文字】\n（图片识别结果仅供参考，请尽量使用清晰度高的图片）\n" + content;
		default:
			throw new RuntimeException("Unrecognized OCR result type: " + type);
		}
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

	private void writeResponse(ResponseStatus status, long startTime, String resContent, Message reqMessage,
			HttpServletResponse response) throws IOException {
		Message resMessage = new Message();
		resMessage.setFromUserName(reqMessage.getToUserName());
		resMessage.setToUserName(reqMessage.getFromUserName());
		resMessage.setCreateTime(reqMessage.getCreateTime());
		resMessage.setMsgType("text");
		resMessage.setContent(resContent);

		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = new PrintWriter(response.getOutputStream(), false, Charset.forName("UTF-8"));
		String resString = XmlUtils.toXml(resMessage);
		writer.print(resString);
		writer.flush();

		log(status, startTime, reqMessage, resContent);
	}

	private void noResponse(Message reqMessage, HttpServletResponse response) {
		log(NULL, null, reqMessage, null);
	}

	private void illegalResponse(Message reqMessage, HttpServletResponse response) {
		log(ILGL, null, null, null);
	}

	private void log(ResponseStatus status, Long startTime, Message reqMessage, String resContent) {
		long cost = startTime == null ? 0 : System.currentTimeMillis() - startTime;

		AbonkontoLog log = new AbonkontoLog();
		log.setTime(new Date());
		log.setStatus(status.name());
		log.setCost((int) cost);
		log.setOpenid(reqMessage == null ? "" : reqMessage.getFromUserName());
		log.setUnionid("");
		log.setMsgType(reqMessage == null ? "" : reqMessage.getMsgType());
		String reqContent;
		switch (reqMessage == null ? "" : reqMessage.getMsgType()) {
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
			reqContent = "";
		}
		log.setRequest(reqContent);
		log.setResponse(resContent == null ? "" : resContent);
		logMapper.insertSelective(log);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
