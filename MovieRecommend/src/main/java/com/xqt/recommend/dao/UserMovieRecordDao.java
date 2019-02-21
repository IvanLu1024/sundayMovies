package com.xqt.recommend.dao;

import com.xqt.recommend.entity.UserMovieRecord;

import java.util.List;

public interface UserMovieRecordDao {
	//获取表中一共有多少条数据
    int count();

    //根据userID  movieId删除一条记录
    int deleteByUserIdAndMovieId(Integer userId, Integer movieId);

    //向表中插入一条记录
    int insert(UserMovieRecord record);

    //根据userId获取数据
    List<UserMovieRecord> selectByUserId(Integer userId);
    
    //根据movieId获取数据
    List<UserMovieRecord> selectByMovieId(Integer MovieId);

    //根据userId  movieId获取某一条数据
    UserMovieRecord selectByUserIdAndMovieId(Integer userId, Integer movieId);
}