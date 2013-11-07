package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;

public interface RoleManageService {
	/**
	 * 获取所有角色
	 * @return
	 * @throws Exception
	 */
	public List<MgrRole> getAllRole() throws Exception;
	/**
	 * 增加或者修改用户角色
	 * @param role角色实体
	 * @return 
	 * @throws Exception
	 */
	public int SaveOrUpdateRole(MgrRole role) throws Exception;
	/**
	 * 根据用户id获取用户
	 * @param role角色实体
	 * @return
	 * @throws Exception
	 */
	public MgrRole getRoleByid(MgrRole role) throws Exception;
	/**
	 * 根据角色id删除角色角色实体
	 * @param role
	 * @throws Exception
	 */
	public void delRoleByid(MgrRole role)throws Exception;
}
