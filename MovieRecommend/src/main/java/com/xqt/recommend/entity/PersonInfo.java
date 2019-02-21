package com.xqt.recommend.entity;


import java.util.Date;

/**
 * 个人信息实体类
 *
 */
public class PersonInfo {
	// 主键ID
	private Long userId;
	// 用户名称
	private String name;
	// 用户头像
	private String profileImg;
	// 用户邮箱
	private String email;
	// 用户性别
	private String gender;
	// 可用状态：0、被禁止 1、可用
	private Integer enableStatus;
	// 1.顾客 2.店家 3.超级管理员
	private Integer userType;
	// 创建时间
	private Date createTime;
	// 最近一次的更新时间
	private Date lastEditTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

}