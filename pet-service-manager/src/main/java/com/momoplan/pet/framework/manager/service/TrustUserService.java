package com.momoplan.pet.framework.manager.service;

import javax.servlet.http.HttpServletRequest;

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
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean,HttpServletRequest request) throws Exception;
	
	/**
	 * 增加或者修改用户
	 * @param petuser
	 * @throws Exception
	 */
	public void addOrUpdatetrust(Petuser petuser,HttpServletRequest request)throws Exception;
	/**
	 * 根据id托管用户信息
	 * @param petuser
	 * @return
	 * @throws Exception
	 */
	  public Petuser getPetUserByid(Petuser petuser)throws Exception;
	  /**
	   * 删除用户信息
	   * @param petuser
	   * @throws Exception
	   */
	  public void delPetUser(Petuser petuser)throws Exception;
}
