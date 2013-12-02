package com.momoplan.pet.framework.feedback.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.commons.web.BaseController;
import com.momoplan.pet.framework.feedback.service.FeedbackService;
import com.momoplan.pet.framework.feedback.service.impl.FeedbackServiceImpl;

public abstract class AbstractHandler extends BaseController implements RequestHandler {
	
	protected FeedbackService feedbackService = Bootstrap.getBean(FeedbackServiceImpl.class);
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	@Resource
	protected CommonConfig commonConfig = null;
	protected Gson gson = MyGson.getInstance();
	
}
