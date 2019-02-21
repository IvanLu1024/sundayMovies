package com.xqt.recommend.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.User;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testGetUserList() {
		List<User> userList = userService.getUserList();
		for(User user:userList) {
			System.out.println(user.getUserName());
		}
	}
}
