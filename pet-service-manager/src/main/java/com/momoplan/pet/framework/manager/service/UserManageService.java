package com.momoplan.pet.framework.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	/**
	 * 修改密码
	 */
	public void upUserPwd(String opassword, String npassword,String rnpassword, HttpServletRequest request) throws Exception;
}

