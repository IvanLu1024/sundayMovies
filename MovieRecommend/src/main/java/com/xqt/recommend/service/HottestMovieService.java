package com.xqt.recommend.service;

import java.util.List;

import com.xqt.recommend.entity.HottestMovie;
import com.xqt.recommend.entity.Movie;

public interface HottestMovieService {
	
	/**
	 * 获取top5
	 * @return
	 */
	List<HottestMovie> getTop5Movie();
	
	/**
	 * 根据movieId选择电影
	 * @param movieId
	 * @return
	 */
	HottestMovie selectMovieById(Integer hottestMovieId);

}
