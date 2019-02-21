package com.xqt.recommend.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.Movie;

public class RecommendMovieRealTimeServiceTest extends BaseTest{

	@Autowired
	private RecommendMovieRealTimeService realTimeService;
	
	@Test
	public void test() {
		List<Movie> movieInfoRealTime = realTimeService.getMovieInfoRealTime(1);
		for(Movie movie:movieInfoRealTime) {
			System.out.println(movie.getMovieName());
		}
	}
	
}
