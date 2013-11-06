package com.momoplan.pet.framework.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.momoplan.pet.commons.domain.manager.po.MgrUser;

public interface UserManageService {
	/**
	 * 获取所有用户
	 * @return
	 * @throws Exception
	 */
	public List<MgrUser> getAllUser() throws Exception;
	/**
	 * 增加或者修改用户
	 * @param ruty传进选择的角色类型
	 * @param mgrUser传入用户对象
	 * @return
	 * @throws Exception
	 */
	public int SaveOrUpdateUser(String[] ruty,MgrUser mgrUser) throws Exception;
	/**
	 * 根据id获取用户信息
	 * @param mgrUser传入用户对象
	 * @return
	 * @throws Exception
	 */
	public MgrUser getUserByid(MgrUser mgrUser) throws Exception;
	/**
	 * 根据id删除对象
	 * @param mgrUser
	 * @throws Exception
	 */
	public void delUserByid(MgrUser mgrUser)throws Exception;
	/**
	 * 修改密码
	 * @param opassword旧密码
	 * @param npassword新密码
	 * @param rnpassword重复新密码
	 * @param request
	 * @throws Exception
	 */
	public void upUserPwd(String opassword, String npassword,String rnpassword, HttpServletRequest request) throws Exception;
}

