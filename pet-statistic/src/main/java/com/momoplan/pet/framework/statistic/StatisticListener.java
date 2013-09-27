package com.momoplan.pet.framework.statistic;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.framework.statistic.service.StatisticService;

@Component
public class StatisticListener implements MessageListener  {
	
	private Logger logger = LoggerFactory.getLogger(StatisticListener.class);
	
	@Autowired
	private StatisticService statisticService ;
	
	@Override
	public void onMessage(Message message) {
		logger.debug(message.toString());
		if( message instanceof TextMessage ){
			TextMessage textMessage = (TextMessage)message;
			try {
				statisticService.testInsert();
				message.acknowledge();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
