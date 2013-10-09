package com.momoplan.pet.framework.wordfilter;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.framework.wordfilter.service.WordFilterService;

@Component
public class MyListener implements MessageListener  {
	
	private Logger logger = LoggerFactory.getLogger(MyListener.class);
	
	@Autowired
	private WordFilterService wordFilterService ;
	
	@Override
	public void onMessage(Message message) {
		logger.debug(message.toString());
		if( message instanceof TextMessage ){
			TextMessage textMessage = (TextMessage)message;
			try {
				logger.debug(textMessage.getText());
				message.acknowledge();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
