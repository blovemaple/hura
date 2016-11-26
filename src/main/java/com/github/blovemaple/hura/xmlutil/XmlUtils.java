package com.github.blovemaple.hura.xmlutil;

import com.thoughtworks.xstream.XStream;

/**
 * Created by GongXunyao on 2015/12/29.<br>
 * from: https://my.oschina.net/jarvan4dev/blog/649555
 */
public class XmlUtils {
	private static final XStream xStream = XStreamFactory.getXStream();

	/**
	 * Javabean 转XML
	 * 
	 * @param t
	 *            待转javabean对象
	 * @param <T>
	 * @return xml字符串
	 */
	public static <T> String toXml(T t) {
		return xStream.toXML(t);
	}

	/**
	 * XML字符串转javabean
	 * 
	 * @param xmlStr
	 *            xml字符串
	 * @param cls
	 *            bean类
	 * @param <T>
	 * @return Java对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xmlStr, Class<T> cls) {
		xStream.processAnnotations(cls);
		return (T) xStream.fromXML(xmlStr);
	}
}