package com.xqt.recommend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.MovieDao;
import com.xqt.recommend.dao.RecommendMovieDao;
import com.xqt.recommend.entity.Movie;
import com.xqt.recommend.service.RecommendMovieService;

@Service
public class RecommendMovieServiceImpl implements RecommendMovieService{

	@Autowired
	private RecommendMovieDao recommendMovieDao;
	
	@Autowired
	private MovieDao movieDao;	
	
	/**
	 * 传入userId, 从数据库中读取为该用户推荐的电影的movieId
	 */
	@Override
	public List<Movie> getMovieByUserId(Integer userId) {
		List<Movie> movieList = new ArrayList<>();
		//获取每部电影的movieId
		List<Integer> movieIdList = recommendMovieDao.getMovieIdListByUserId(userId);
		//通过每部电影movieId将movie信息获取出来
		for(Integer movieId:movieIdList) {
			movieList.add(movieDao.selectMovieById(movieId));
		}
		
		return movieList;
	}
}
