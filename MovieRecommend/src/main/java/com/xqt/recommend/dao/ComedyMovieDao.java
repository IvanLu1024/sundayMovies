package com.xqt.recommend.dao;

import java.util.List;

import com.xqt.recommend.entity.ActionMovie;

public interface ComedyMovieDao   {
	
	//获取对应的表中条目数目
	int count();

	//根据table_inner_id选择出相应的电影条目
    List<ActionMovie> selectBytableInnerId(Integer startTableInnerId);

}