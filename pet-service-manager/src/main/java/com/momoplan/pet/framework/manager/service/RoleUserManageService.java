package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;


public interface RoleUserManageService {
	/**
	 * 根据关系表id查找
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public MgrUserRoleRel getRoleUserByid(MgrUserRoleRel userRoleRel) throws Exception;
	/**
	 * 根据关系表的角色id获取关系集合
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyRoleid(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 根据关系表的用户id获取关系集合
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyUserid(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 增加或者修改关系
	 * @param userRoleRel
	 * @throws Exception
	 */
	public void saveOrUpdateRoleUser(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 删除关系
	 * @param userRoleRel
	 * @throws Exception
	 */
	public void delRoleUser(MgrUserRoleRel userRoleRel)throws Exception;

}
