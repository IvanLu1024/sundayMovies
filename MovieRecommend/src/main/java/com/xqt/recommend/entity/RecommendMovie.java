package com.xqt.recommend.entity;

/**
 * 获取给不同id用户推荐的电影的实体类
 * 
 *对应数据库中tb_recommend_movie
 * @author Administrator
 *
 */
public class RecommendMovie {
	
	private Integer userId;
	
	private Integer movieId;
	
	private Double rate;

	public RecommendMovie() {
	}
	
	public RecommendMovie(Integer userId, Integer movieId, Double rate) {
		this.userId = userId;
		this.movieId = movieId;
		this.rate = rate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	

}
