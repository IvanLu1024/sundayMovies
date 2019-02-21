package com.xqt.recommend.web.local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xqt.recommend.dto.LocalAuthExecution;
import com.xqt.recommend.entity.LocalAuth;
import com.xqt.recommend.entity.PersonInfo;
import com.xqt.recommend.enums.LocalAuthStateEnum;
import com.xqt.recommend.service.LocalAuthService;
import com.xqt.recommend.util.CodeUtil;
import com.xqt.recommend.util.HttpServletRequestUtil;



@Controller
@RequestMapping(value="local",method= {RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;
	@RequestMapping(value="/bindlocalauth",method=RequestMethod.POST)
	@ResponseBody
	/***
	 * 将用户信息与平台账号绑定
	 * @param request
	 * @return
	 */
	private Map<String,Object>bindLocalAuth(HttpServletRequest request){
		Map<String,Object>modelMap=new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName=HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		//从Session中获取用户信息
		System.out.println("================"+userName);
		System.out.println("================"+password);
		PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		System.out.println("获取到Session?"+(request.getSession()==null));
		if(userName!=null && password !=null &&user!=null &&user.getUserId()!=null) {
			LocalAuth localAuth=new LocalAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			LocalAuthExecution le= localAuthService.bindLocalAuth(localAuth);
			System.out.println(userName);
			if(le.getState()==LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg",le.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		
		return modelMap;
	}
	@RequestMapping(value="/changelocalpwd",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>changeLocalPwd(HttpServletRequest request){
		System.out.println("ENTER");
		Map<String,Object>modelMap=new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName=HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		String newPassword=HttpServletRequestUtil.getString(request,"newPassword");
		PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		if(user==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "您未登录");
			return modelMap;
		}
		System.out.println(user==null);
		System.out.println("SESSION1:"+user.getUserId());
		System.out.println("SESSION2:"+user.getName());
		
		if(userName!=null && password!=null && newPassword!=null && 
				user !=null && user.getUserId()!=null &&
				!password.equals(newPassword)) {
			try {
				LocalAuth localAuth=localAuthService.getLocalAuthByUserId(user.getUserId());
				System.out.println(localAuth.getUsername());
				System.out.println("-/-/-/-/");
				System.out.println(userName);
				if(localAuth==null||!localAuth.getUsername().equals(userName)) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的账号非本次登陆的账号");
					return modelMap;
				}
				LocalAuthExecution le=localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
				if(le.getState()==LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());					
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	@RequestMapping(value="/logincheck",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>logincheck(HttpServletRequest request){
		System.out.println("Enter!");
		Map<String,Object>modelMap=new HashMap<String,Object>();
		boolean needVerify=HttpServletRequestUtil.getBoolean(request, "needVerify");
		if(needVerify&&!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userName=HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		if(userName!=null && password!=null) {
			LocalAuth localAuth=localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if(localAuth!=null) {
				//登录成功之后，将userId存储在json中，返回给前台页面
				modelMap.put("success", true);
				System.out.println("------->"+localAuth.getPersonInfo().getName());
				System.out.println("------->"+localAuth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
				PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
				modelMap.put("userName", userName);
				Long userId=user.getUserId();
				modelMap.put("userId", userId);
				System.out.println("JustNow_Session:"+user.getUserId());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>logout(HttpServletRequest request){
		Map<String,Object>modelMap=new HashMap<String,Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
	
	@RequestMapping(value="/registercheck",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object>register(HttpServletRequest request){
		System.out.println("EnterRegisterController:");	
		Map<String,Object>modelMap=new HashMap<String,Object>();
		LocalAuth localAuth=new LocalAuth();
		System.out.println(request==null);
		String userName=HttpServletRequestUtil.getString(request, "userName");
		String password=HttpServletRequestUtil.getString(request, "password");
		String perefenceStr=HttpServletRequestUtil.getString(request, "perefenceStr");
		System.out.println("RRRRRRRRRREEEEEEEEEEEGGGGGGGGGGGGGG");
		System.out.println(perefenceStr);
		String[] perefenceStrArray=perefenceStr.split(",");
		Integer[] perefenceIntArray=new Integer[perefenceStrArray.length];
		for(int i=0;i<perefenceStrArray.length;i++) {
			perefenceIntArray[i]=Integer.valueOf(perefenceStrArray[i]);
			System.out.println(perefenceIntArray[i]);
		}	
		System.out.println("Get From HTML:"+userName);
		System.out.println("Get From HTML:"+password);
		
		LocalAuth checkUserExists=localAuthService.getLocalAuthByUserName(userName);
		if(checkUserExists!=null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名已存在");
			return modelMap;
		}
		
		Date createTime=new Date();
		Date lastEditTime=new Date();
		PersonInfo personInfo=new PersonInfo();
		
		
		personInfo.setName(userName);
		personInfo.setEmail(HttpServletRequestUtil.getString(request, "email"));
		personInfo.setGender(HttpServletRequestUtil.getString(request, "gender"));
		personInfo.setEnableStatus(1);
		personInfo.setUserType(2);
		personInfo.setProfileImg(null);

		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		localAuth.setUsername(userName);
		localAuth.setPassword(password);
		localAuth.setCreateTime(createTime);
		localAuth.setLastEditTime(lastEditTime);
		localAuth.setPersonInfo(personInfo);

		LocalAuthExecution le=localAuthService.register(localAuth, null,perefenceIntArray);
		if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", le.getStateInfo());
		}
		return modelMap;
	}
}

