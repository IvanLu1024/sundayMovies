package com.xqt.recommend.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.User;

public class UserDaoTest extends BaseTest{
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testQueryUser() {
		List<User> userList = userDao.queryUser();
		for(User user:userList) {
			System.out.println(user.getUserName());
		}
	}

}
