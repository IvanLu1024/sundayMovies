package com.xqt.recommend.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.ActionMovie;

public class ActionMovieDaoTest extends BaseTest{
	
	@Autowired
	private ActionMovieDao actionMovieDao;
	
	@Test
//	@Ignore
	public void testSelectBytableInnerId() {
		List<ActionMovie> actionMovies = actionMovieDao.selectBytableInnerId(100);
		for(ActionMovie actionMovie:actionMovies) {
			System.out.println(actionMovie.getMovieName());
		}
	}
	
	@Test
	@Ignore
	public void testCount() {
		System.out.println(actionMovieDao.count());
	}

}
