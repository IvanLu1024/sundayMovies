package com.xqt.recommend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccessToken {
	//收到的凭证
	@JsonProperty("access_token")
	private String accessToken;
	//凭证有效时间，单位:秒
	@JsonProperty("expires_in")
	private String expires_in;
	//表示更新令牌，用来获取下一次的访问令牌，这里没多大用处
	@JsonProperty("refresh_token")
	private String refresh_token;
	//该用户在此公共号下的身份标识，对于此微信号具有唯一性
	@JsonProperty("openid")
	private String openid;
	//表示权限范围，这里可以省略
	@JsonProperty("scope")
	private String scope;
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	public String getOpenid() {
		return openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}


}
