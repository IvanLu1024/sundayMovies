package com.xqt.recommend.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.xqt.recommend.entity.LocalAuth;


public interface LocalAuthDao {

	/**
	 * 通过用户名和密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName,
			@Param("password") String password);

	
	LocalAuth queryLocalByUserName(@Param("userName") String userName);
	/**
	 * 通过userId来查询用户
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * 
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);

	/**
	 * 
	 * @param localAuth
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId,
			@Param("userName") String userName,
			@Param("password") String password,
			@Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
