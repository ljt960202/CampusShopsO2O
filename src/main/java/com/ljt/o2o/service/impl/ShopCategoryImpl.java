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
import com.ljt.o2o.dao.ShopCategoryDao;
import com.ljt.o2o.entity.ShopCategory;
import com.ljt.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryImpl implements ShopCategoryService {

	private static final Logger logger = LoggerFactory.getLogger(ShopCategoryImpl.class);
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Autowired
	private JedisUtil.Keys jedisKeys;
	
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key = SHOPCATEGORYKEY;
		List<ShopCategory> shopCategoryList = null;
		String jsonString = null;
		ObjectMapper objectMapper = new ObjectMapper();
		//拼接出redis的key
		if(shopCategoryCondition == null) {
			//若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
			key = key + "_allfirstlevel";
		}else if(shopCategoryCondition != null && shopCategoryCondition.getParent()!=null 
				&& shopCategoryCondition.getParent().getShopCategoryId() == null) {
			//若parentId为空，则列出该parentId下的所以子类别
			key = key + "_parent"+shopCategoryCondition.getParent().getShopCategoryId();
		}else if(shopCategoryCondition != null) {
			//列出所有子类别，不管其属于哪个类，都列出来
			key = key + "_allsecondlevel";
		}
		if(!jedisKeys.exists(key)) {
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			try {
				jsonString = objectMapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}else {
			jsonString = jedisStrings.get(key);
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList = objectMapper.readValue(jsonString, javaType);
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
		return shopCategoryList;
	}

}
