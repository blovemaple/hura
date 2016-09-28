package com.github.blovemaple.hura.xmlutil;

import com.thoughtworks.xstream.XStream;

/**
 * Created by GongXunyao on 2015/12/29.<br>
 * from: https://my.oschina.net/jarvan4dev/blog/649555
 */
public class XmlUtils {
	private static final XStream xStream = XStreamFactory.getXStream();

	/**
	 * Javabean תXML
	 * 
	 * @param t
	 *            ��תjavabean����
	 * @param <T>
	 * @return xml�ַ���
	 */
	public static <T> String toXml(T t) {
		return xStream.toXML(t);
	}

	/**
	 * XML�ַ���תjavabean
	 * 
	 * @param xmlStr
	 *            xml�ַ���
	 * @param cls
	 *            bean��
	 * @param <T>
	 * @return Java����
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xmlStr, Class<T> cls) {
		xStream.processAnnotations(cls);
		return (T) xStream.fromXML(xmlStr);
	}
}