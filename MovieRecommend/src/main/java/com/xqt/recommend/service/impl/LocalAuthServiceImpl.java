package com.xqt.recommend.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xqt.recommend.dao.LocalAuthDao;
import com.xqt.recommend.dao.PersonInfoDao;
import com.xqt.recommend.dao.UserPerefenceDao;
import com.xqt.recommend.dto.LocalAuthExecution;
import com.xqt.recommend.entity.LocalAuth;
import com.xqt.recommend.entity.PersonInfo;
import com.xqt.recommend.entity.UserPerefence;
import com.xqt.recommend.enums.LocalAuthStateEnum;
import com.xqt.recommend.service.LocalAuthService;
//import com.xqt.recommend.util.FileUtil;
//import com.xqt.recommend.util.ImageUtil;





@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private UserPerefenceDao userPerefenceDao;
	
	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName,
			String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, password);
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	public LocalAuth getLocalAuthByUserName(String userName) {
		return localAuthDao.queryLocalByUserName(userName);
	}
	
	@Override
	@Transactional
	public LocalAuthExecution register(LocalAuth localAuth,
			CommonsMultipartFile profileImg,Integer[] perefence) throws RuntimeException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUsername() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(localAuth.getPassword());
			if (localAuth.getPersonInfo() != null
					&& localAuth.getPersonInfo().getUserId() == null) {
				if (profileImg != null) {
					localAuth.getPersonInfo().setCreateTime(new Date());
					localAuth.getPersonInfo().setLastEditTime(new Date());
					localAuth.getPersonInfo().setEnableStatus(1);
					try {
//						addProfileImg(localAuth, profileImg);
					} catch (Exception e) {
						throw new RuntimeException("addUserProfileImg error: "
								+ e.getMessage());
					}
				}
				try {
					PersonInfo personInfo = localAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					//localAuth.setUserId(personInfo.getUserId());
					if (effectedNum <= 0) {
						throw new RuntimeException("添加用户信息失败");
					}
				} catch (Exception e) {
					throw new RuntimeException("insertPersonInfo error: "
							+ e.getMessage());
				}
			}
			
			
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号创建失败");
			} else {
				
				
				for(int i=0;i<perefence.length;i++) {
					UserPerefence userPerefence=new UserPerefence();
					userPerefence.setUserId(localAuth.getPersonInfo().getUserId());
					userPerefence.setMovieType(perefence[i]);
					userPerefenceDao.insertUserPerefence(userPerefence);
					userPerefence=null;
					System.out.println("喜好添加成功");
				}
				
				
				
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUsername() == null ||localAuth.getPersonInfo()==null
				|| localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo()
				.getUserId());
		if (tempAuth != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(localAuth.getPassword());
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
			String password, String newPassword) {
		if (userId != null && userName != null && password != null
				&& newPassword != null && !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId,
						userName, password,
						newPassword, new Date());
				System.out.println(effectedNum);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

}
