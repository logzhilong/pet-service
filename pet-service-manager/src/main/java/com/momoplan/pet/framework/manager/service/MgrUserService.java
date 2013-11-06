package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;

public interface MgrUserService {
	/**
	 * 根据用户id获取该用户的所属角色
	 * 
	 * @param userId用户id
	 * @return
	 * @throws Exception
	 */
	public List<MgrRole> getRoleByUserId(String userId) throws Exception;

	/**
	 * 根据用户name获取用户
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public MgrUser getMgrUserByName(String username) throws Exception;

}
