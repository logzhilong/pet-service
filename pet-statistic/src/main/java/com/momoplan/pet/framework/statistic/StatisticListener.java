package com.momoplan.pet.framework.statistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.framework.statistic.service.StatisticService;

@Component
public class StatisticListener{

	private Logger logger = LoggerFactory.getLogger(StatisticListener.class);

	@Autowired
	private StatisticService statisticService;

	
	/**
	 * 
	 * 根据消息参数调用对应的处理方法
	 * @param request
	 * @param ret
	 * @throws Exception
	 */
	public void petStatisticHandle(String request) throws Exception{
		logger.debug("petStatisticHandle starting...");
//		ClientRequest clientRequest = new ObjectMapper().reader(ClientRequest.class).readValue(request);
//		statisticService.mergeUsageState(clientRequest);
	}

}