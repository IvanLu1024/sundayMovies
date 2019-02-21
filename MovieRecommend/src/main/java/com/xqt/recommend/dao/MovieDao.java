package com.xqt.recommend.dao;

import java.util.List;

import com.xqt.recommend.entity.Movie;

public interface MovieDao {
    /**
     * 选择综合类别中top10电影
     * @return
     */
    List<Movie> selectTop10Movie();

    /**
     * 根据movieId选择电影，进行电影详情的展示
     * @param movieId
     * @return
     */
    Movie selectMovieById(Integer movieId);

}