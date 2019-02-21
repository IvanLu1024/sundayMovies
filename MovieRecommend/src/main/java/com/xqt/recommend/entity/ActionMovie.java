package com.xqt.recommend.entity;

import java.util.Date;

public class ActionMovie {
	private Integer tableInnerId;

	private Integer movieId;

    private String movieName;
    
    private Integer releaseTime;

    private String movieImgAddr;
    
    private String moviePlayUrl;

    private String movieInfo;

    private Date lastEditTime;
    
    public Integer getTableInnerId() {
		return tableInnerId;
	}

	public void setTableInnerId(Integer tableInnerId) {
		this.tableInnerId = tableInnerId;
	}

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
        this.movieName = movieName == null ? null : movieName.trim();
    }

    public String getMovieImgAddr() {
        return movieImgAddr;
    }

    public void setMovieImgAddr(String movieImgAddr) {
        this.movieImgAddr = movieImgAddr == null ? null : movieImgAddr.trim();
    }

    public String getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(String movieInfo) {
        this.movieInfo = movieInfo == null ? null : movieInfo.trim();
    }

    public String getMoviePlayUrl() {
        return moviePlayUrl;
    }

    public void setMoviePlayUrl(String moviePlayUrl) {
        this.moviePlayUrl = moviePlayUrl == null ? null : moviePlayUrl.trim();
    }

    public Integer getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}