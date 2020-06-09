package com.ljt.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ljt.o2o.dto.LocalAuthExecution;
import com.ljt.o2o.entity.LocalAuth;
import com.ljt.o2o.exception.LocalAuthOperationException;

public interface LocalAuthService {
	/**
	 * 
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 * 
	 * @param localAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution register(LocalAuth localAuth,
			CommonsMultipartFile profileImg) throws LocalAuthOperationException;

	/**
	 * 
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthOperationException;

	/**
	 * 
	 * @param localAuthId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String userName,
			String password, String newPassword) throws LocalAuthOperationException;
}
