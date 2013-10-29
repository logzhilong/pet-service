package com.momoplan.pet.framework.feedback.service.impl;


import java.util.Date;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.feedback.common.Constants;
import com.momoplan.pet.framework.feedback.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	protected JmsTemplate apprequestTemplate;
	@Override
	public String feedback(ClientRequest clientRequest,
			SsoAuthenticationToken authenticationToken) throws Exception {
		String userid = authenticationToken.getUserid();
		String feedback = PetUtil.getParameter(clientRequest, "feedback");
		String email = PetUtil.getParameter(clientRequest, "email");
		Date createTime = new Date();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userid",userid);
		jsonObj.put("feedback",feedback);
		jsonObj.put("email",email);
		jsonObj.put("createTime",createTime);
		TextMessage tm = new ActiveMQTextMessage();
		tm.setText(jsonObj.toString());
		ActiveMQQueue queue = new ActiveMQQueue();
		queue.setPhysicalName(Constants.PET_PUSH_TO_XMPP);
		apprequestTemplate.convertAndSend(queue, tm);
		logger.info("user's feedbacks :"+jsonObj);
		return null;
	}

	
}