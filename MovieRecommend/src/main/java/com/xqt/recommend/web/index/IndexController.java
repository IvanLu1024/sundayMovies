package com.xqt.recommend.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用来解析路由并转发到相应的html中
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping(value = "/frontend/index", method=RequestMethod.GET)
	public String index() {
		return "/frontend/index";
	}
	
	@RequestMapping(value = "/frontend/moviedetail", method=RequestMethod.GET)
	public String movieDetail(String movieId) {

		return "/frontend/moviedetail";
	}
	
	@RequestMapping(value = "/frontend/shoplist", method=RequestMethod.GET)
	public String shopList() {
		return "/frontend/shoplist";
	}

}
