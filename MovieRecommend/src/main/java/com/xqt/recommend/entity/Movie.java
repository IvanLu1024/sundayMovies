package com.xqt.recommend.entity;

import java.util.Date;

public class Movie {
	
	//电影ID
	private Integer movieId;
	//电影名称
	private String movieName;
	//电影简介
	private String movieInfo;
	//电影海报地址
	private String movieImgAddr;
	//电影播放链接
	private String moviePlayUrl;
	//电影上映时间
	private String releaseTime;
	//更新时间
	private Date lastEditTime;
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieInfo() {
		return movieInfo;
	}
	public void setMovieInfo(String movieInfo) {
		this.movieInfo = movieInfo;
	}
	public String getMovieImgAddr() {
		return movieImgAddr;
	}
	public void setMovieImgAddr(String movieImgAddr) {
		this.movieImgAddr = movieImgAddr;
	}
	public String getMoviePlayUrl() {
		return moviePlayUrl;
	}
	public void setMoviePlayUrl(String moviePlayUrl) {
		this.moviePlayUrl = moviePlayUrl;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
}
