package com.github.blovemaple.hura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.blovemaple.hura.vortaro.Vortaro;
import com.github.blovemaple.hura.vortaro.Wiktionary;
import com.github.blovemaple.hura.xmlutil.XmlUtils;

public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Vortaro vortaro = new Wiktionary();

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
					writeResponse("Bonvenon!\nMi estas interaktiva vortaro de Esperanto. Bonvolu sendi vorton al mi. :)",
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
				String result = vortaro.query(reqContent);
				if (result != null && !result.isEmpty()) {
					writeResponse(result, message, response);
				} else {
					writeResponse("Mi ne povas trovi ĉi tiun vorton. :(", message, response);
				}
				return;
			} else {
				writeResponse("Bonvolu sendi vorton. :)", message, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			writeResponse("Mi ne povas rekoni vian enigon. :(", message, response);
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
