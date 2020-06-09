package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.LocalAuth;
import com.ljt.o2o.entity.PersonInfo;

public class LocalAuthDaoTest extends BaseTest {

	@Autowired
	private LocalAuthDao localAuthDao;
	
	private static final String USERNAME = "testusername";
	private static final String PASSWORD = "testpassword";
	
	@Test
	public void testInsertLocalAuth() {
		//新增一条平台信息
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		localAuth.setUserName(USERNAME);
		localAuth.setPassword(PASSWORD);
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		personInfo.setUserId(2L);
		localAuth.setPersonInfo(personInfo);
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testQueryLocalByUserNameAndPwd() {
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(USERNAME, PASSWORD);
		assertEquals("西多头", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testQueryLocalByUserId() {
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(2L);
		assertEquals("西多头", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testUpdateLocalAuth() {
		int effectedNum = localAuthDao.updateLocalAuth(2L, USERNAME, PASSWORD, PASSWORD+"new", new Date());
		assertEquals(1, effectedNum);
	}
}
