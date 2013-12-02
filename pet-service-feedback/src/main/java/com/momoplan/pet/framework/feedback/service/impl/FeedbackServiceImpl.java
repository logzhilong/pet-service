package com.momoplan.pet.framework.feedback.service.impl;


import java.util.Date;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.framework.feedback.common.Constants;
import com.momoplan.pet.framework.feedback.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	private static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	@Autowired
	protected JmsTemplate apprequestTemplate;
	@Override
	public void feedback(String feedback,String email,String userid) throws Exception {
		Date createTime = new Date();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userid",userid);
		jsonObj.put("feedback",feedback);
		jsonObj.put("email",email);
		jsonObj.put("createTime",createTime);
		String json = MyGson.getInstance().toJson(jsonObj);
		try {
			logger.info("user's feedbacks :"+json);
			TextMessage tm = new ActiveMQTextMessage();
			tm.setText(json.toString());
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName(Constants.PET_FEEDBACK);
			apprequestTemplate.convertAndSend(queue, tm);
		} catch (Exception e) {
			logger.error("jms send error",e);
			throw e;
		}
	}

	
}
