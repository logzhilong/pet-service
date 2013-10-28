package com.momoplan.pet.framework.servicestate.service;

import java.util.List;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.CacheKeysConstance;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;

public interface StateService extends CacheKeysConstance{

	void delUserState(ClientRequest clientRequest) throws Exception;
	int countReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	void delReply(ClientRequest clientRequest) throws Exception;
	boolean reportContent(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	
	/**
	 * 添加回复
	 * @param reply
	 * @return
	 * @throws Exception
	 */
	public String addReply(StatesUserStatesReply reply) throws Exception;
	/**
	 * 添加用户动态
	 * @param clientRequest
	 * @param authenticationToken
	 * @return
	 * @throws Exception
	 */
	public String addUserState(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	/**
	 * 获取用户动态，isSelf==true 获取自己的动态，isSelf==false 则非自己的动态
	 * @param userid
	 * @param pageSize
	 * @param pageNo
	 * @param isSelf
	 * @return
	 * @throws Exception
	 */
	public List<StatesUserStatesVo> getUserStates(String userid,int pageSize,int pageNo,boolean isSelf) throws Exception;
	/**
	 * modify by liangc 2013-10-26
	 * 获取全部好友的，动态的集合
	 * @param userid
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public List<StatesUserStatesVo> getAllFriendStates(String userid,int pageSize,int pageNo) throws Exception;
	/**
	 * 获取单条动态信息
	 * @param userid
	 * @param stateid
	 * @return
	 * @throws Exception
	 */
	public StatesUserStatesVo findOneState(String userid,String stateid) throws Exception;
	
}
