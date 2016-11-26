package com.github.blovemaple.hura.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 获取配置的工具。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class Conf {
	private static final Map<String, Properties> PROPS = new HashMap<>();

	public static String str(String file, String name) throws IOException {
		Properties prop = PROPS.get(file);
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(Conf.class.getResourceAsStream("/" + file + ".properties"));
			} catch (NullPointerException e) {
				throw new IOException("Lack of conf file: " + file + ".properties");
			}
			PROPS.put(file, prop);
		}
		return prop.getProperty(name);
	}

}
