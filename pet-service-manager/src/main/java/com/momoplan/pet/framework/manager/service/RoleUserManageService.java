package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;


public interface RoleUserManageService {
	/**
	 * 根据用户关系表id查找用户对应的角色
	 * @param userRoleRel角色关系表实体
	 * @return
	 * @throws Exception
	 */
	public MgrUserRoleRel getRoleUserByid(MgrUserRoleRel userRoleRel) throws Exception;
	/**
	 * 根据关系表的角色id获取关系集合
	 * @param userRoleRel角色关系表实体
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyRoleid(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 根据关系表的用户id获取关系集合
	 * TODO能够和在一起,暂时如此
	 * @param userRoleRel角色关系表实体
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyUserid(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 增加或者修改关系
	 * @param userRoleRel角色关系表实体
	 * @throws Exception
	 */
	public void saveOrUpdateRoleUser(MgrUserRoleRel userRoleRel)throws Exception;
	/**
	 * 删除关系
	 * @param userRoleRel角色关系表实体
	 * @throws Exception
	 */
	public void delRoleUser(MgrUserRoleRel userRoleRel)throws Exception;

}
