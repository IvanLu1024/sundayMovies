package com.xqt.recommend.web.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.xqt.recommend.service.PersonInfoService;
import com.xqt.recommend.util.wechat.SignUtil;

import com.xqt.recommend.service.WechatAuthService;



@Controller
@RequestMapping("wechat")
public class WeiXinController {

	private static Logger log = LoggerFactory.getLogger(WeiXinController.class);

	@Resource
	private WechatAuthService WechatAuthService;

	@Resource
	private PersonInfoService personInfoService;

	@RequestMapping(method = { RequestMethod.GET })
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin get...");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				log.debug("weixin get success....");
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}

	/*@RequestMapping(method = { RequestMethod.POST }, produces = "application/xml;charset=UTF-8")
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 调用核心业务类接收消息、处理消息
			TextMessage textMessage = CoreServiceUtil.processRequest(request);
			String respMessage = MessageUtil.textMessageToXml(textMessage);
			// 响应消息
			PrintWriter out = response.getWriter();
			out.print(respMessage);
			out.close();
			Point point = textMessage.getPoint();
			if (point != null) {
				try {
					String openId = textMessage.getOpenId();
					log.debug("openId:" + openId);
					log.debug("service:" + WechatAuthService);
					WechatAuth auth = WechatAuthService
							.getWechatAuthByOpenId(openId);
					log.debug("auth:" + auth);
					if (auth == null) {
						PersonInfo personInfo = new PersonInfo();
						personInfo.setName(null);
						personInfo.setGender(null);
						personInfo.setProfileImg(null);
						personInfo.setEnableStatus(1);
						personInfo.setCustomerFlag(1);
						personInfo.setShopOwnerFlag(0);
						personInfo.setAdminFlag(0);
						auth = new WechatAuth();
						auth.setOpenId(openId);
						auth.setPersonInfo(personInfo);
						WechatAuthService.register(auth, null);
					}
				} catch (Exception e) {
					log.debug("exception:" + e.getMessage());
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
