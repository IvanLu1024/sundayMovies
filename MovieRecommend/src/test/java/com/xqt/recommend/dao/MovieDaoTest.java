package com.xqt.recommend.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.Movie;

public class MovieDaoTest extends BaseTest{
	
	@Autowired
	private MovieDao movieDao;
	
	@Test
	@Ignore
	public void testSelectTop10Movie() {
		List<Movie> movies = movieDao.selectTop10Movie();
		for(Movie movie:movies) {
			System.out.println(movie.getReleaseTime());
		}
	}
	
	@Test
	public void testSelectMovieById() {
		Movie movie = movieDao.selectMovieById(1);
		System.out.println(movie.getMovieName());
	}
}
