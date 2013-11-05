package com.momoplan.pet.framework.manager.service;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.Petuser;



public interface TrustUserService {
	/**
	 * 获取所有用户
	 * @param pageBean
	 * @param trustUser
	 * @return
	 * @throws Exception
	 */
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean) throws Exception;
	
	/**
	 * 增加或者修改用户
	 * @param petuser
	 * @throws Exception
	 */
	public void addOrUpdatetrust(Petuser petuser)throws Exception;
}
