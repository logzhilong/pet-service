package com.momoplan.pet.framework.servicestate.service;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.CacheKeysConstance;
import com.momoplan.pet.framework.servicestate.vo.StateResponse;

public interface StateService extends CacheKeysConstance{

	String addUserState(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	int delUserState(ClientRequest clientRequest) throws Exception;
	String addReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	int countReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	int delReply(ClientRequest clientRequest) throws Exception;
	StateResponse findMyStates(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	StateResponse findFriendStates(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	StateResponse getAllFriendStates(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	StateResponse findOneState(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	StateResponse getRepliesByTimeIndex(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
	boolean reportContent(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception;
}
