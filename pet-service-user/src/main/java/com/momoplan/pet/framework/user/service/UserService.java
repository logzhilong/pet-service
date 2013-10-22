package com.momoplan.pet.framework.user.service;

import java.util.List;

import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.user.CacheKeysConstance;
import com.momoplan.pet.framework.user.vo.UserVo;

/**
 * @author liangc
 */
public interface UserService extends CacheKeysConstance{
	/**
	 * 更新用户信息
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(SsoUser user) throws Exception ;
	/**
	 * 更新用户坐标
	 * @param userId
	 * @param longitude
	 * @param latitude
	 * @throws Exception
	 */
	public void updateUserLocation(String userId,double longitude,double latitude) throws Exception;
	/**
	 * 获取用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public UserVo getUser(SsoAuthenticationToken tokenObj) throws Exception ;
	
	public void updatePetInfo(PetInfo petInfo)throws Exception ;
	
	public void savePetInfo(PetInfo petInfo)throws Exception ;
	
	public List<PetInfo> getPetInfo(String userid)throws Exception ;
	
}