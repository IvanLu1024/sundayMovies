package com.xqt.recommend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xqt.recommend.dao.HottestMovieDao;
import com.xqt.recommend.entity.HottestMovie;
import com.xqt.recommend.service.HottestMovieService;

@Service
public class HottestMovieServiceImpl implements HottestMovieService{

	@Autowired
	private HottestMovieDao hottestMovieDao;
	
	@Override
	public List<HottestMovie> getTop5Movie() {
		return hottestMovieDao.selectTop5Movie();
	}

	@Override
	public HottestMovie selectMovieById(Integer hottestMovieId) {
		return hottestMovieDao.selectMovieById(hottestMovieId);
	}


}
