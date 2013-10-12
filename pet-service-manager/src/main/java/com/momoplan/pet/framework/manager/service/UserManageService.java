package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;

public interface UserManageService {
	/**
	 * 获取所有用户
	 * @return
	 * @throws Exception
	 */
	public List<MgrUser> getAllUser() throws Exception;
	/**
	 * 增加或者修改用户
	 * @param role
	 * @return 
	 * @throws Exception
	 */
	public int SaveOrUpdateUser(String[] ruty,MgrUser mgrUser) throws Exception;
	/**
	 * 根据用户id获取用户
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public MgrUser getUserByid(MgrUser mgrUser) throws Exception;
	/**
	 * 根据用户id删除用户
	 * @param role
	 * @throws Exception
	 */
	public void delUserByid(MgrUser mgrUser)throws Exception;
	
}
