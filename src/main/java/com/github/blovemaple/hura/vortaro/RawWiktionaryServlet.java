package com.github.blovemaple.hura.vortaro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RawWiktionaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Wiktionary wiki = new Wiktionary();

	public RawWiktionaryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String raw = wiki.queryRaw(new String(request.getParameter("vorto").getBytes("ISO-8859-1"), "UTF-8"));
			response.getWriter().write(raw);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
