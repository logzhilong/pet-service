package com.momoplan.pet.framework.feedback.service;


import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.feedback.CacheKeysConstance;

public interface FeedbackService extends CacheKeysConstance{

	String feedback(ClientRequest clientRequest, SsoAuthenticationToken authenticationToken) throws Exception;
	
}