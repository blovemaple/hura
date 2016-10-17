package com.github.blovemaple.hura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.blovemaple.hura.vortaro.ChenVortaro;
import com.github.blovemaple.hura.vortaro.Vortaro;
import com.github.blovemaple.hura.xmlutil.XmlUtils;

public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Vortaro vortaro = new ChenVortaro(); // new Wiktionary();

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
					writeResponse(
							"Bonvenon!\nMi estas interaktiva vortaro de Esperanto. Bonvolu sendi esperantan aŭ la ĉinan vorton al mi.\n"
							+ "欢迎关注Hura！Hura是一个世界语汉语双向词典，向我发送世界语单词或汉语词语即可得到解释。 :)",
							message, response);
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
				String result = vortaro.query(formatWord(reqContent));
				if (result != null && !result.isEmpty()) {
					writeResponse(result, message, response);
				} else {
					writeResponse("Mi ne povas trovi ĉi tiun vorton.\n抱歉，我查不到你输入的单词。 :(", message, response);
				}
				return;
			} else {
				writeResponse("Bonvolu sendi vorton.\n请输入正确的单词哦。 :)", message, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			writeResponse("Mi ne povas rekoni vian enigon.\n抱歉，我看不懂你在写什么。 :(", message, response);
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

	private static final Map<String, String> REPLACE_LETTERS = new HashMap<>();
	static {
		REPLACE_LETTERS.put("cx", "ĉ");
		REPLACE_LETTERS.put("gx", "ĝ");
		REPLACE_LETTERS.put("hx", "ĥ");
		REPLACE_LETTERS.put("jx", "ĵ");
		REPLACE_LETTERS.put("sx", "ŝ");
		REPLACE_LETTERS.put("ux", "ŭ");
		REPLACE_LETTERS.put("ch", "ĉ");
		REPLACE_LETTERS.put("gh", "ĝ");
		REPLACE_LETTERS.put("hh", "ĥ");
		REPLACE_LETTERS.put("jh", "ĵ");
		REPLACE_LETTERS.put("sh", "ŝ");
		REPLACE_LETTERS.put("uh", "ŭ");
		REPLACE_LETTERS.put("au", "aŭ");
		REPLACE_LETTERS.put("eu", "eŭ");
	}

	private static String formatWord(String reqContent) {
		String word = reqContent.trim().toLowerCase();
		for (Map.Entry<String, String> replace : REPLACE_LETTERS.entrySet())
			word = word.replaceAll(replace.getKey(), replace.getValue());
		return word;
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
