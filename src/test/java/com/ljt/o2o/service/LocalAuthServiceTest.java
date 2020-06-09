package com.ljt.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.dto.LocalAuthExecution;
import com.ljt.o2o.entity.LocalAuth;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.enums.WechatAuthStateEnum;

public class LocalAuthServiceTest extends BaseTest{

	@Autowired
	private LocalAuthService localAuthService;
	
	@Test
	public void testBindLocalAuth() {
		//新增平台信息
		LocalAuth localAuth = new LocalAuth();
		String username = "lijintao";
		String password = "Ljt1996020.";
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(13L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setUserName(username);
		localAuth.setPassword(password);
		LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), localAuthExecution.getState());
		localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
		System.out.println("用户昵称："+localAuth.getPersonInfo().getName());
		System.out.println("平台账号密码:"+localAuth.getPassword());
	}
	
	@Test
	public void testModifyLocalAuth() {
		//设置账号信息
		long userId = 13L;
		String userName = "lijintao";
		String password = "Ljt1996020.";
		String newPassword = "Ljt1996020";
		LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(userId, userName, password, newPassword);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), localAuthExecution.getState());
		LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, newPassword);
		System.out.println(localAuth.getPersonInfo().getName());
	}

	
}
