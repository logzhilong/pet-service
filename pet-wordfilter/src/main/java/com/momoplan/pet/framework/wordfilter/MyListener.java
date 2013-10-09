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
				String biz = textMessage.getStringProperty("biz");
				String bid = textMessage.getStringProperty("bid");
				String content = textMessage.getStringProperty("content");
				logger.debug("消息内容 biz="+biz+";bid="+bid+";content="+content);
				message.acknowledge();
				wordFilterService.doFilter(biz, bid, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
