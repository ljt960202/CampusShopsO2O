package com.ljt.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.o2o.cache.JedisUtil;
import com.ljt.o2o.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService{

	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Override
	public void removeFromService(String keyPrefix) {
		Set<String> keys = jedisKeys.keys(keyPrefix+"*");
		for(String key:keys) {
			if(jedisKeys.exists(key)) {
				jedisKeys.del(key);
			}
		}
	}

}
