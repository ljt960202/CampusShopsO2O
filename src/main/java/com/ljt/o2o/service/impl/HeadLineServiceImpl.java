package com.ljt.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljt.o2o.cache.JedisUtil;
import com.ljt.o2o.dao.HeadLineDao;
import com.ljt.o2o.entity.HeadLine;
import com.ljt.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	
	
	private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);
	@Autowired
	private HeadLineDao headLineDao;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Override
	public List<HeadLine> queryHeadList(HeadLine headLineCondition) {
		String key = HEADLINELISTKEY;
		if(headLineCondition!=null&&headLineCondition.getEnableStatus()!=null) {
			key = key +"_"+headLineCondition.getEnableStatus();
		}
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null; 
		if(!jedisKeys.exists(key)) {
			headLineList = headLineDao.queryHeadLine(headLineCondition);
			try {
				jsonString = mapper.writeValueAsString(headLineList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			jedisStrings.set(key, jsonString);
		}else {
			jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,HeadLine.class);
			try {
				headLineList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return headLineList;
	}

}
