package com.xqt.recommend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.MovieDao;
import com.xqt.recommend.dao.RecommendMovieRealTimeDao;
import com.xqt.recommend.entity.Movie;
import com.xqt.recommend.entity.RecommendMovieRealTime;
import com.xqt.recommend.service.RecommendMovieRealTimeService;

@Service
public class RecommendMovieRealTimeServiceImpl implements RecommendMovieRealTimeService{

	@Autowired
	private RecommendMovieRealTimeDao recommendMovieRealTimeDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@Override
	public List<Movie> getMovieInfoRealTime(Integer userId) {
		List<Movie> movieList = new ArrayList<>();
		//获取实时推荐的电影--5部
		List<RecommendMovieRealTime> realTimeMovieList = recommendMovieRealTimeDao.selectMovieByUserId(userId);
		
		for(RecommendMovieRealTime movie:realTimeMovieList) {
			movieList.add(movieDao.selectMovieById(movie.getMovieId()));
		}
		
		return movieList;
	}

}
