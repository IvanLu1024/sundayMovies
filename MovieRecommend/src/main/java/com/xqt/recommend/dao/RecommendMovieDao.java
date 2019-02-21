package com.xqt.recommend.dao;

import java.util.List;

public interface RecommendMovieDao {
	
	//根据userId，从数据库中选择数据movieId
	List<Integer> getMovieIdListByUserId(Integer userId);
	

}
