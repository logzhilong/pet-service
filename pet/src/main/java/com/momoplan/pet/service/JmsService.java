package com.momoplan.pet.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsService {

	
	@Produce(uri = "jms:topic:apprequest")
	ProducerTemplate apprequestProducerTemplate;
	
	public void sendJms(String body,String ret){
		apprequestProducerTemplate.sendBodyAndHeader(body, "ret", ret);
	}
}
