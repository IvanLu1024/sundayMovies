package com.xqt.recommend.service;

import com.xqt.recommend.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

	/**
	 * 
	 * @param personInfoCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
/*	PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition,
			int pageIndex, int pageSize);*/

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	/*PersonInfoExecution addPersonInfo(PersonInfo personInfo);*/

	/**
	 * 
	 * @param personInfo
	 * @return
	 */
	/*PersonInfoExecution modifyPersonInfo(PersonInfo personInfo);
*/
}
