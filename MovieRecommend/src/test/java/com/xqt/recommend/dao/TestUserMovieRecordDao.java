package com.xqt.recommend.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.UserMovieRecord;

public class TestUserMovieRecordDao extends BaseTest{
	
	@Autowired
	private UserMovieRecordDao userMovieRecordDao;
	
	@Test
	public void testInsert() {
		UserMovieRecord userMovieRecord = new UserMovieRecord();
		
		userMovieRecord.setUserId(100);
		userMovieRecord.setMovieId(100);
		userMovieRecord.setUserMovieRate(5);
		userMovieRecord.setRecordTimestamp(new Date());
		
		int colum = userMovieRecordDao.insert(userMovieRecord);
		System.out.println(colum);
	}

}
