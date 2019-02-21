package com.xqt.recommend.web.movieadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xqt.recommend.dao.HottestMovieDao;
import com.xqt.recommend.dao.UserMovieRecordDao;
import com.xqt.recommend.entity.HottestMovie;
import com.xqt.recommend.entity.Movie;
import com.xqt.recommend.entity.UserMovieRecord;
import com.xqt.recommend.service.ActionMovieService;
import com.xqt.recommend.service.ComedyMovieService;
import com.xqt.recommend.service.CrimeMovieService;
import com.xqt.recommend.service.FantasyMovieService;
import com.xqt.recommend.service.HottestMovieService;
import com.xqt.recommend.service.MovieService;
import com.xqt.recommend.service.MusicalMovieService;
import com.xqt.recommend.service.RecommendMovieRealTimeService;
import com.xqt.recommend.service.RecommendMovieService;
import com.xqt.recommend.service.RomanceMovieService;
import com.xqt.recommend.service.ThrillerMovieService;
import com.xqt.recommend.service.WarMovieService;
import com.xqt.recommend.util.HttpServletRequestUtil;


@Controller
@RequestMapping("/movieadmin")
public class MovieAdminController {
	private static Logger logger=Logger.getLogger(MovieAdminController.class);
	
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private HottestMovieService hottestMovieService;
	
	@Autowired
	private ActionMovieService actionMovieService;
	@Autowired
	private ComedyMovieService comedyMovieService;
	@Autowired
	private CrimeMovieService crimeMovieService;
	@Autowired
	private FantasyMovieService fantasyMovieService;
	@Autowired
	private MusicalMovieService musicalMovieService;
	@Autowired
	private RomanceMovieService romanceMovieService;
	@Autowired
	private ThrillerMovieService thrillerMovieService;
	@Autowired
	private WarMovieService warMovieService;
	
	@Autowired 
	private UserMovieRecordDao userMovieRecordDao;
	
	@Autowired
	private RecommendMovieService recommendMovieService;
	
	@Autowired
	private RecommendMovieRealTimeService recommendMovieRealTimeService;
	
	@RequestMapping(value = "/top10movie", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> top10Movie() {
		//游客模式
		//调用movieService的方法，获取top10
		Map<String, Object> modelMap = new HashMap<>();
		List<Movie> movieList = new ArrayList<>();
		try {
			movieList = movieService.getTop10Movie();
			modelMap.put("success", true);
			modelMap.put("total", movieList.size());
			modelMap.put("movieList", movieList);
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "MovieAdminController--top10Movie："+e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/gethottestmovie", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getHottestMovie(){
		Map<String, Object> modelMap = new HashMap<>();
		List<HottestMovie> hottestMovieList = hottestMovieService.getTop5Movie();
		
		if (hottestMovieList.size() != 0) {
			modelMap.put("success", true);
			modelMap.put("total", hottestMovieList.size());
			modelMap.put("top5Moive", hottestMovieList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "MovieAdminController--get5FromTop10Movie：返回的top10中没有电影");
		}
		return modelMap;
	}
	
	/**
	 * 获取电影详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/gethottestmovieinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getMovieInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer movieId = HttpServletRequestUtil.getInt(request, "hottestMovieId");
		HottestMovie movie = hottestMovieService.selectMovieById(movieId);
		if (movie != null) {
			modelMap.put("success", true);
			modelMap.put("movieInfo", movie);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", movie);
		}
		return modelMap;
	}
	
	/**
	 * 根据前端传递的request，获取categoryId
	 * 然后获取10部该种类的电影，返回给前端
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getmoviebycategory", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getMovieByCategory(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer categoryId = HttpServletRequestUtil.getInt(request, "categoryId");
		List<Movie> movieList = null;
		switch (categoryId) {
		case 1:
			movieList = actionMovieService.get10ActionMovie();
			break;
		case 2:
			movieList = comedyMovieService.get10ActionMovie();
			break;
		case 3:
			movieList = crimeMovieService.get10ActionMovie();
			break;
		case 4:
			movieList = fantasyMovieService.get10ActionMovie();
			break;
		case 5:
			movieList = musicalMovieService.get10ActionMovie();
			break;
		case 6:
			movieList = romanceMovieService.get10ActionMovie();
			break;
		case 7:
			movieList = thrillerMovieService.get10ActionMovie();
			break;
		case 8:
			movieList = warMovieService.get10ActionMovie();
			break;
		default:
			break;
		}
		
		if (movieList.size() > 0) {
			modelMap.put("success", true);
			modelMap.put("total", movieList.size());
			modelMap.put("movieList", movieList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "actionMovieList.size() = 0");
		}
		
		return modelMap;
	}
	
	//接收从前端传递来的数据(userId,movieId,rate)，然后以日志形式输出，并向数据库中添加数据
	@RequestMapping(value = "/usermovierate", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	private Map<String, Object> getUserMovieRate(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer movieId = HttpServletRequestUtil.getInt(request, "movieId");
		Integer userId = HttpServletRequestUtil.getInt(request, "userId");
		Integer rate = HttpServletRequestUtil.getInt(request, "rate");
		Date date = new Date();
		
		UserMovieRecord userMovieRecord = new UserMovieRecord(userId, movieId, rate, date);
		
		//输出日志
		logger.info(userId+","+movieId+","+rate+","+date.getTime());
		//向数据库添加
		try {
			int colum = userMovieRecordDao.insert(userMovieRecord);
			if (colum == 1) {
				modelMap.put("success", true);
				modelMap.put("Msg", "MovieAdminController-getUserMovieRate-向数据库中添加数据成功");
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "MovieAdminController-getUserMovieRate-向数据库中添加数据失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "MovieAdminController-getUserMovieRate"+e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getrecommendmovie", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getRecommendMovie(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer userId = HttpServletRequestUtil.getInt(request, "userId");
		List<Movie> movieList ;
		try {
			movieList = recommendMovieService.getMovieByUserId(userId);
			modelMap.put("success", true);
			modelMap.put("total", movieList.size());
			modelMap.put("movieList", movieList);
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "MovieAdminController--getRecommendMovie："+e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getrecommendmovierealtime", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getRecommendMovieRealTime(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		Integer userId = HttpServletRequestUtil.getInt(request, "userId");
		List<Movie> movieList ;
		try {
			movieList = recommendMovieRealTimeService.getMovieInfoRealTime(userId);
			modelMap.put("success", true);
			modelMap.put("total", movieList.size());
			modelMap.put("movieList", movieList);
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "MovieAdminController--getRecommendMovieRealTime："+e.getMessage());
		}
		return modelMap;
	}
}
