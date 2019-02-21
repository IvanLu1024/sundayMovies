package com.xqt.recommend.web.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xqt.recommend.dto.UserAccessToken;
import com.xqt.recommend.dto.WechatAuthExecution;
import com.xqt.recommend.dto.WechatUser;
import com.xqt.recommend.entity.PersonInfo;
import com.xqt.recommend.entity.WechatAuth;
import com.xqt.recommend.enums.WechatAuthStateEnum;
import com.xqt.recommend.service.PersonInfoService;
import com.xqt.recommend.service.WechatAuthService;
import com.xqt.recommend.util.wechat.WechatUtil;


@Controller
@RequestMapping("wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * @author lixiang
 *
 */
public class WechatLoginController {

	private static Logger log = LoggerFactory
			.getLogger(WechatLoginController.class);
/*
	@Resource
	private PersonInfoService personInfoService;
	@Resource
	private WechatAuthService WechatAuthService;

	@Resource
	private ShopService shopService;

	@Resource
	private ShopAuthMapService shopAuthMapService;

	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";*/
	//private static final String FRONTEND = "1";
	//private static final String SHOPEND = "2";
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private WechatAuthService wechatAuthService;
	
	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		String code = request.getParameter("code");
		//String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatAuth auth = null;
		WechatUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				String accessToken = token.getAccessToken();
				openId = token.getOpenid();
				user=WechatUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (Exception e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "
						+ e.toString());
				e.printStackTrace();
			}
		}
		//若微信账号为空，需要注册微信账号，同时注册用户信息
		if(auth==null) {
			PersonInfo personInfo = WechatUtil
					.getPersonInfoFromRequest(user);
			auth=new WechatAuth();
			auth.setOpenId(openId);
			
			/*if(FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			}else {
				personInfo.setUserType(2);
			}*/
			
			auth.setPersonInfo(personInfo);
			WechatAuthExecution we=wechatAuthService.register(auth);
			
			if(we.getState()!=WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			}else {
				personInfo=personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
			}
		}else {
			PersonInfo personInfo = WechatUtil
					.getPersonInfoFromRequest(user);
			request.getSession().setAttribute("user", personInfo);
		}
		
		return "frontend/index";
		/*if (FRONTEND.equals(roleType)) {
			return "frontend/index";
		}else {
			return "shopadmin/shoplist";
		}*/
		
		//          ===============
		/*if(user!=null) {
			return "frontend/index";
		}else {
			return null;
		}*/
		
		
		
		/*if(auth==null) {
			PersonInfo personInfo = WechatUtil
					.getPersonInfoFromRequest(user);
			auth=new WechatAuth();
			auth.setOpenId(openId);
			
			if(FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			}else {
				personInfo.setUserType(2);
			}
			
			auth.setPersonInfo(personInfo);
			WechatAuthExecution we=wechatAuthService.register(auth);
			
			if(we.getState()!=WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			}else {
				personInfo=personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
			}
		}*/
		
		
		/*if (FRONTEND.equals(roleType)) {
			return "frontend/index";
		}else {
			return "shopadmin/shoplist";
		}
		*/	
		/*if (FRONTEND.equals(roleType)) {
			
			if (auth == null) {
				personInfo.setCustomerFlag(1);
				auth = new WechatAuth();
				auth.setOpenId(openId);
				auth.setPersonInfo(personInfo);
				WechatAuthExecution we = WechatAuthService.register(auth, null);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", personInfo);
			return "frontend/index";
		}
		if (SHOPEND.equals(roleType)) {
			PersonInfo personInfo = null;
			WechatAuthExecution we = null;
			if (auth == null) {
				auth = new WechatAuth();
				auth.setOpenId(openId);
				personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
				personInfo.setShopOwnerFlag(1);
				auth.setPersonInfo(personInfo);
				we = WechatAuthService.register(auth, null);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			*/
		/*if(user!=null) {
			return "frontend/index";
		}else {
			return null;
		}*/
		/*log.debug("weixin login success.");
		log.debug("login role_type:" + roleType);
		if (FRONTEND.equals(roleType)) {
			PersonInfo personInfo = WeiXinUserUtil
					.getPersonInfoFromRequest(user);
			if (auth == null) {
				personInfo.setCustomerFlag(1);
				auth = new WechatAuth();
				auth.setOpenId(openId);
				auth.setPersonInfo(personInfo);
				WechatAuthExecution we = WechatAuthService.register(auth, null);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", personInfo);
			return "frontend/index";
		}
		if (SHOPEND.equals(roleType)) {
			PersonInfo personInfo = null;
			WechatAuthExecution we = null;
			if (auth == null) {
				auth = new WechatAuth();
				auth.setOpenId(openId);
				personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
				personInfo.setShopOwnerFlag(1);
				auth.setPersonInfo(personInfo);
				we = WechatAuthService.register(auth, null);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", personInfo);
			ShopExecution se = shopService.getByEmployeeId(personInfo
					.getUserId());
			request.getSession().setAttribute("user", personInfo);
			if (se.getShopList() == null || se.getShopList().size() <= 0) {
				return "shop/registershop";
			} else {
				request.getSession().setAttribute("shopList", se.getShopList());
				return "shop/shoplist";
			}
		}
		return null;
	}*/
	}
	
}
