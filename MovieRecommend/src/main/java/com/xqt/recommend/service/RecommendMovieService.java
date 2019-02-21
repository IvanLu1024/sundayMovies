package com.xqt.recommend.service;

import java.util.List;

import com.xqt.recommend.entity.Movie;

public interface RecommendMovieService {
	
	//根据userId选择出来的10个movieId，将movie从数据库中读取出来
	List<Movie> getMovieByUserId(Integer userId);

}
