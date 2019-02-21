package com.xqt.recommend.dao;

import java.util.List;

import com.xqt.recommend.entity.HottestMovie;
import com.xqt.recommend.entity.Movie;

public interface HottestMovieDao {
    /**
     * 选择hottest movie类别中top5电影
     * @return
     */
    List<HottestMovie> selectTop5Movie();

    /**
     * 根据movieId选择电影，进行电影详情的展示
     * @param movieId
     * @return
     */
    HottestMovie selectMovieById(Integer hottestMovieId);

}