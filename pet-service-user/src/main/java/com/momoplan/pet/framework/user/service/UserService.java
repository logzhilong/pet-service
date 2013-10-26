package com.momoplan.pet.framework.user.service;

import java.util.List;

import org.json.JSONArray;

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
	/**
	 * 更新宠物信息
	 * @param petInfo
	 * @throws Exception
	 */
	public void updatePetInfo(PetInfo petInfo)throws Exception ;
	/**
	 * 新增宠物信息
	 * @param petInfo
	 * @throws Exception
	 */
	public void savePetInfo(PetInfo petInfo)throws Exception ;
	/**
	 * 获取宠物列表
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<PetInfo> getPetInfo(String userid)throws Exception ;
	/**
	 * 删除宠物ID，同事也得修正索引
	 * @param id
	 * @throws Exception
	 */
	public void delPetInfo(String id)throws Exception;
	/**
	 * 添加或删除好友信息，根据 st 判断动作
	 * @param st
	 * @param aid
	 * @param bid
	 * @throws Exception
	 */
	public void addOrRemoveFriend(String st,String aid,String bid) throws Exception ;
	/**
	 * 向IOS推送消息
	 * @param fromname
	 * @param toname
	 * @param msg
	 * @throws Exception
	 */
	public void pushMsgApn(String fromname,String toname,String msg) throws Exception;
	/**
	 * 获取好友列表
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<UserVo> getFirendList(String userid) throws Exception;

	/**
	 * 获取附近的人，两个查询条件，分别是性别、宠物类型
	 * @param userId
	 * @param gender 性别
	 * @param petType 宠物类型
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	public JSONArray getNearPerson(String pageIndex,String userId,String gender,String petType, double longitude, double latitude,String personOrPet) throws Exception;
	
	
}