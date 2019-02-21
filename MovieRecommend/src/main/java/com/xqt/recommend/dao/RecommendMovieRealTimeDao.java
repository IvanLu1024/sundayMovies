package com.xqt.recommend.dao;

import java.util.List;

import com.xqt.recommend.entity.RecommendMovieRealTime;


public interface RecommendMovieRealTimeDao {

	//根据table_inner_id选择出相应的电影条目
    List<RecommendMovieRealTime> selectMovieByUserId(Integer UserId);
	
}
