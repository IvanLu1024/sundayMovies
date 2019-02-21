package com.xqt.recommend.service;

import java.util.List;

import com.xqt.recommend.entity.Movie;

public interface RecommendMovieRealTimeService {
	
	/**
	 * 根据userId，获取实时推荐的movieId
	 * 然后根据movieId获取每一部电影信息
	 * @param userId
	 * @return
	 */
	List<Movie> getMovieInfoRealTime(Integer userId);

}
