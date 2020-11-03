package com.github.blovemaple.hura.vortaroapi;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blovemaple.hura.dal.ProgrametoQueryLog;
import com.github.blovemaple.hura.dal.ProgrametoQueryLogMapper;
import com.github.blovemaple.hura.source.ChenQueryResult;
import com.github.blovemaple.hura.source.ChenQueryResult.ListItem;
import com.github.blovemaple.hura.source.ChenVortaro;
import com.github.blovemaple.hura.util.Language;
import com.github.blovemaple.hura.util.PrivateConf;

/**
 * 给陈在伟老师提供的API服务。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@RestController
@RequestMapping("/vortaro")
public class VortaroApiService {
	private static final Logger logger = LoggerFactory.getLogger(VortaroApiService.class);

	@Autowired
	private PrivateConf privateConf;
	@Autowired
	private ProgrametoQueryLogMapper queryLogMapper;

	private ChenVortaro chenVortaro = new ChenVortaro();
	private ObjectMapper jackson = new ObjectMapper();

	@RequestMapping("/query")
	public ChenQueryResult query(@RequestParam("key") String key, @RequestParam("str") String query,
			@RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
			@RequestParam(name = "matchtype", required = false, defaultValue = "0") Integer matchType) {
		if (!privateConf.getVortaroApiKey().equals(key)) {
			logger.info("Invalid key for vortaro query: " + key);
			return ChenQueryResult.failed(query);
		}

		long startTime = System.currentTimeMillis();

		if (query == null || query.isBlank())
			return ChenQueryResult.success(query, Collections.emptyList());

		String formattedQuery = query.trim();
		ChenQueryResult result;
		try {
			List<ListItem> items = chenVortaro.queryResultFromDB(formattedQuery, Language.determine(formattedQuery),
					limit, matchType);
			result = ChenQueryResult.success(query, items);
		} catch (Exception e) {
			logger.error("Error query chen vortaro data.", e);
			return ChenQueryResult.failed(query);
		}

		ProgrametoQueryLog log = new ProgrametoQueryLog();
		log.setTime(new Date());
		log.setCost((int) (System.currentTimeMillis() - startTime));
		log.setOpenid("chen");
		log.setUnionid("");
		log.setQuery(query);
		log.setSectionKey(ChenVortaro.class.getSimpleName().toLowerCase());
		log.setIsDetail(false);
		log.setHasResult(!result.getList().isEmpty());
		try {
			log.setResult(jackson.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			logger.error("Error serializing result into log.", e);
		}
		queryLogMapper.insertSelective(log);

		return result;
	}
}
