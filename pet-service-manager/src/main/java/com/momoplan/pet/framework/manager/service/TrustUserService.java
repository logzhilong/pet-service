package com.momoplan.pet.framework.manager.service;

import javax.servlet.http.HttpServletRequest;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.Petuser;



public interface TrustUserService {
	/**
	 * 获取所有(当前用户下)托管用户
	 * @param pageBean将数据迅如pb,做分页
	 * @param trustUser
	 * @return
	 * @throws Exception
	 */
	public PageBean<MgrTrustUser> AllTrustUser(PageBean<MgrTrustUser> pageBean,HttpServletRequest request) throws Exception;
	
	/**
	 * 增加或者修改托管用户
	 * @param petuser
	 * @throws Exception
	 */
	public int  addOrUpdatetrust(Petuser petuser,HttpServletRequest request)throws Exception;
	/**
	 * 根据id获取托管用户信息
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
