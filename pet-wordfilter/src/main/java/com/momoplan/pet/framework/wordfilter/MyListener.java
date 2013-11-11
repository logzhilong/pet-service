package com.momoplan.pet.framework.wordfilter;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.json.JSONObject;
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
				String json = textMessage.getText();
				logger.debug("消息内容 json="+json);
				JSONObject j = new JSONObject(json);
				String biz = j.getString("biz");
				String bid = j.getString("bid");
				String content = j.getString("content");
				message.acknowledge();
				wordFilterService.doFilter(biz, bid, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
