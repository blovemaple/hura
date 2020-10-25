package com.github.blovemaple.hura.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = { "classpath:private.properties" })
public class PrivateConf {

	@Value("${wechat.programeto.appid}")
	private String wxProgrametoAppid;
	@Value("${wechat.programeto.secret}")
	private String wxProgrametoSecret;
	
	@Value("${vortaroapi.key}")
	private String vortaroApiKey;

	public String getWxProgrametoAppid() {
		return wxProgrametoAppid;
	}

	public String getWxProgrametoSecret() {
		return wxProgrametoSecret;
	}

	public String getVortaroApiKey() {
		return vortaroApiKey;
	}

}
