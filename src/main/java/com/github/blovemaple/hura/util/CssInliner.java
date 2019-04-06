package com.github.blovemaple.hura.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.css.NormalOutput;
import org.fit.cssbox.css.Output;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.io.StreamDocumentSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class CssInliner {
	
	public static void main(String[] args) throws IOException {
		String html = Files.readString(Paths.get("E:\\vortaro.html"));
		String css = Files.readString(Paths.get("E:\\common.css"));
		String result = inlineCss(html, css);
		Files.writeString(Paths.get("E:\\out-0.html"), result);
	}

	/**
	 * CSS内联到HTML，返回内联后的HTML。<br>
	 * 参考：https://stackoverflow.com/a/13308089
	 */
	public static String inlineCss(String html, String css) {

		try {
			DocumentSource docSource = new StreamDocumentSource(new ByteArrayInputStream(html.getBytes("UTF-8")), null,
					"text/html");

			// Parse the input document
			DOMSource parser = new DefaultDOMSource(docSource);
			Document doc = parser.parse();

			// Create the CSS analyzer
			DOMAnalyzer da = new DOMAnalyzer(doc, docSource.getURL());
			da.attributesToStyles(); // convert the HTML presentation attributes to inline styles
			da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT); // use the standard style sheet
			da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT); // use the additional style
																						// sheet

			da.addStyleSheet(null, css, DOMAnalyzer.Origin.AGENT); // use the standard style sheet
			da.getStyleSheets(); // load the author style sheets

			// Compute the styles
			System.err.println("Computing style...");
			da.stylesToDomInherited();

			// output
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			Output out = new NormalOutput(doc);
			out.dumpTo(bo);
			return new String(bo.toByteArray(), "UTF-8");
		} catch (IOException | SAXException e) {
			// impossible
			e.printStackTrace();
			return null;
		}
	}

}
