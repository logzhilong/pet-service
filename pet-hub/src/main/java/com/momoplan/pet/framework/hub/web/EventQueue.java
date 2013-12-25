package com.momoplan.pet.framework.hub.web;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventQueue {

	private static Logger logger = LoggerFactory.getLogger(EventQueue.class);
	@Autowired
	private JmsTemplate apprequestTemplate;

	private BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(4096);
    
    /**
     * 消息入队
     * @param alarmMessageVO
     * @return
     */
    public boolean push(String json) {
        return blockingQueue.offer(json);
    }
    
    @PostConstruct
    private void consumer(){
    	new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						String json = blockingQueue.take();
						logger.debug(json);
						TextMessage tm = new ActiveMQTextMessage();
						tm.setText(json);
						apprequestTemplate.convertAndSend(tm);
					} catch (InterruptedException e) {
						logger.error("EventQueue InterruptedException",e);
					} catch (JMSException e) {
						logger.error("EventQueue JMSException",e);
					}
				}
			}
		}).start();
    }
    
}
