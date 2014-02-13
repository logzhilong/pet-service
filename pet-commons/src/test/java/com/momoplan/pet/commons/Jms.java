package com.momoplan.pet.commons;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;


public class Jms {
	
//	{
//		"from":"发送人",
//		"pwd":"发送人登录口令",
//		"to":"接收人",
//		"domain":"域",
//		"body":"实体摘要",
//		"id":"实体ID",
//		"type":"实体类型",
//		"expire":"有效时长，单位秒"
//	}
	public static void main(String[] args) throws JMSException, JSONException {
		JSONObject json = new JSONObject();
		json.put("from", "liangc@a.test.com");
		json.put("to", "cc@a.test.com");
		json.put("body", "summary");//实体摘要
		json.put("id", "1234567890");//实体ID
		json.put("type", "bbs_note");//实体类型
		json.put("expire", "3600");//如果离线，那么过期时间为 expire 秒之后
		String message = json.toString();
		
		System.out.println(message);
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = cf.createConnection();
		connection.start();

		Session session = connection.createSession(Boolean.TRUE,Session.CLIENT_ACKNOWLEDGE);
		
		Destination destination = session.createQueue("python-xmpp-notify-queue");
		
		MessageProducer producer = session.createProducer(destination);

		TextMessage textMessage = session.createTextMessage(message);	
		producer.send(textMessage);
		
		session.commit();
		session.close();
		connection.close();
	}
	
}
