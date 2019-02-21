package com.xqt.recommend.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;

public class RecommendMovieDaoTest extends BaseTest{
	
	@Autowired
	private RecommendMovieDao recommendMovieDao;
	
	@Test
	public void testGetMovieIdListByUserId() {
		List<Integer> movieIdList = recommendMovieDao.getMovieIdListByUserId(1);
		for(int i:movieIdList) {
			System.out.println(i);
		}
		
	}

}
