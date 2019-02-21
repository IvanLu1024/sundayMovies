package com.xqt.recommend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.ActionMovieDao;
import com.xqt.recommend.dao.MovieDao;
import com.xqt.recommend.entity.ActionMovie;
import com.xqt.recommend.entity.Movie;
import com.xqt.recommend.service.WarMovieService;

@Service
public class WarMovieServiceImpl implements WarMovieService{
	
	@Autowired
	private ActionMovieDao actionMovieDao;
	
	@Autowired
	private MovieDao movieDao;

	@Override
	/**
	 * 产生一个随机数，向后选取10部电影
	 */
	public List<Movie> get10ActionMovie() {
		Integer tableCount = getTableCount();
		//产生一个在1--tableCount中的随机数
		int x=1+(int)(Math.random()*tableCount);
		//如果x过大，就无法取到10条数据，
		//此处确保能取到10条数据
		if (x >= tableCount-10) {
			x -= 10;
		}
		List<ActionMovie> actionMovieList= actionMovieDao.selectBytableInnerId(x);
		List<Movie> movieList = new ArrayList<>();
		
		for(ActionMovie actionMovie:actionMovieList) {
			movieList.add(movieDao.selectMovieById(actionMovie.getMovieId()));
		}
		
		return movieList;
	}

	/**
	 * 得到表中的条目总数
	 */
	private Integer getTableCount() {
		return actionMovieDao.count();
	}

	@Override
	public ActionMovie selectActionMovieById(Integer movieId) {
		// TODO Auto-generated method stub
		return null;
	}

}
