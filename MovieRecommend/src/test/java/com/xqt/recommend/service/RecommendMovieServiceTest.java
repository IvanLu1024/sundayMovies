package com.xqt.recommend.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.BaseTest;
import com.xqt.recommend.entity.Movie;

public class RecommendMovieServiceTest extends BaseTest{

	@Autowired
	private RecommendMovieService recommendMovieService;
	
	@Test
	public void testGetMovieById() {
		List<Movie> movieList = recommendMovieService.getMovieByUserId(2);
		for(Movie movie:movieList) {
			System.out.println(movie.getMovieId()+"-"+movie.getMovieName());
		}
	}
}
