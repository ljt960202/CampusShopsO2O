package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.entity.WechatAuth;

public class WechatAuthDaoTest extends BaseTest {

	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Test
	public void testInsertWechatAuth() {
		//新增一个微信账号
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(2L);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId("sdasffdqweqwg243");
		wechatAuth.setCreateTime(new Date());
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQueryWechatInfoByOpenId() {
		WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("sdasffdqweqwg243");
		String name = wechatAuth.getPersonInfo().getName();
		assertEquals("西多头", name);
	}
}
