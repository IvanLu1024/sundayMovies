package com.xqt.recommend.dao;



import java.util.List;

import com.xqt.recommend.entity.User;

public interface UserDao {

	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> queryUser();
}
