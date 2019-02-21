package com.xqt.recommend.entity;

public class UserPerefence {
	private Long perefenceId;
	//用户ID
	private Long userId;
	//喜好类型
	private Integer movieType;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getMovieType() {
		return movieType;
	}
	public void setMovieType(Integer movieType) {
		this.movieType = movieType;
	}
	public Long getPerefenceId() {
		return perefenceId;
	}
	public void setPerefenceId(Long perefenceId) {
		this.perefenceId = perefenceId;
	}
	
	
	
}
