package com.xqt.recommend.dto;

import java.util.List;

import com.xqt.recommend.entity.LocalAuth;
import com.xqt.recommend.enums.LocalAuthStateEnum;


public class LocalAuthExecution {
	// 缁撴灉鐘舵��
	private int state;

	// 鐘舵�佹爣璇�
	private String stateInfo;

	private int count;

	private LocalAuth localAuth;

	private List<LocalAuth> localAuthList;

	public LocalAuthExecution() {
	}

	// 澶辫触鐨勬瀯閫犲櫒
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 鎴愬姛鐨勬瀯閫犲櫒
	public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuth = localAuth;
	}

	// 鎴愬姛鐨勬瀯閫犲櫒
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,
			List<LocalAuth> localAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuthList = localAuthList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalAuth getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}

	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}

	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}

}
