package com.xqt.recommend.service;

import java.util.List;

import com.xqt.recommend.entity.ActionMovie;
import com.xqt.recommend.entity.Movie;

public interface RomanceMovieService {
	
	/**
	 * 从数据库中随机选择出10部电影
	 * @return
	 */
	List<Movie> get10ActionMovie();
	
	/**
	 * 根据movieId从数据库中获取到某一部电影的详细信息
	 * @param movieId
	 * @return
	 */
	ActionMovie selectActionMovieById(Integer movieId);
}
