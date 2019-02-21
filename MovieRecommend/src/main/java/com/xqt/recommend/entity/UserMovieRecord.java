package com.xqt.recommend.entity;

import java.util.Date;

public class UserMovieRecord {
	
	private Integer userId;
	
	private Integer movieId;
	
	private Integer userMovieRate;

    private Date recordTimestamp;
	
    public UserMovieRecord(Integer userId, Integer movieId, Integer rate, Date date) {
		this.userId = userId;
		this.movieId = movieId;
		this.userMovieRate = rate;
		this.recordTimestamp = date;
	}
    
 
	public UserMovieRecord() {
		
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

    public Integer getUserMovieRate() {
        return userMovieRate;
    }

    public void setUserMovieRate(Integer userMovieRate) {
        this.userMovieRate = userMovieRate;
    }

    public Date getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(Date recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }
}