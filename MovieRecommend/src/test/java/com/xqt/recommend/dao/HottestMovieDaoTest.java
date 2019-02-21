package com.xqt.recommend.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.HottestMovie;

public class HottestMovieDaoTest extends BaseTest{

	@Autowired
	private HottestMovieDao hottestMovieDao;
	
	@Test
	public void testSelectMovieById() {
		HottestMovie hottestMovie = hottestMovieDao.selectMovieById(1);
		System.out.println(hottestMovie.getMovieName());
		
	}
}
