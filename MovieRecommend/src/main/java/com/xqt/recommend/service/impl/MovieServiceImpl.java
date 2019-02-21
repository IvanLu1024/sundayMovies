package com.xqt.recommend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.MovieDao;
import com.xqt.recommend.entity.Movie;
import com.xqt.recommend.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public List<Movie> getTop10Movie() {
		return movieDao.selectTop10Movie();
	}

	@Override
	public Movie selectMovieById(Integer movieId) {
		return movieDao.selectMovieById(movieId);
	}

}
