package com.xqt.recommend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xqt.recommend.dao.MovieDao;
import com.xqt.recommend.entity.Movie;

public interface MovieService {
	
	/**
	 * 获取top10
	 * @return
	 */
	List<Movie> getTop10Movie();
	
	/**
	 * 根据movieId选择电影
	 * @param movieId
	 * @return
	 */
	Movie selectMovieById(Integer movieId);

}
