package com.xqt.recommend.web.useradmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xqt.recommend.entity.User;
import com.xqt.recommend.service.UserService;

@Controller
@RequestMapping("/useradmin")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/listuser", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUser() {
		Map<String, Object> modelMap = new HashMap<>();
		List<User> list = new ArrayList<>();
		try {
			list = userService.getUserList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "UserController--listUser()"+e.toString());
		}
		return modelMap;
	}

}
