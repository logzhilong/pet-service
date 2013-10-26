package com.momoplan.pet.framework.pat.service;

import java.util.List;

import com.momoplan.pet.commons.domain.user.po.SsoUser;


/**
 * @author liangc
 */
public interface PatService {
	/**
	 * 添加赞
	 * @param userid
	 * @param srcid
	 * @throws Exception
	 */
	public void addPat(String userid,String srcid,String type) throws Exception;
	/**
	 * 删除赞
	 * @param userid
	 * @param srcid
	 * @throws Exception
	 */
	public void delPat(String userid,String srcid) throws Exception;
	/**
	 * 获取赞
	 * @param srcid
	 * @return
	 * @throws Exception
	 */
	public List<SsoUser> getPat(String srcid) throws Exception;
	
}