package com.xqt.recommend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.UserDao;
import com.xqt.recommend.entity.User;
import com.xqt.recommend.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getUserList() {
		return userDao.queryUser();
	}

}
