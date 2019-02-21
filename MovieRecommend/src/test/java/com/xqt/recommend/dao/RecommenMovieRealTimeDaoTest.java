package com.xqt.recommend.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.RecommendMovieRealTime;

public class RecommenMovieRealTimeDaoTest extends BaseTest{

	@Autowired
	private RecommendMovieRealTimeDao recommendMovieRealTimeDao;
	
	@Test
	public void testSelectMovieByUserId() {
		List<RecommendMovieRealTime> movieList= recommendMovieRealTimeDao.selectMovieByUserId(1);
		System.out.println("=============================================="+movieList.size());
	}
}
