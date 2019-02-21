package com.xqt.recommend.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xqt.recommend.dto.WechatAuthExecution;
import com.xqt.recommend.entity.WechatAuth;



public interface WechatAuthService {

	/**
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 
	 * @param wechatAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException;

}
