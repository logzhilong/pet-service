package com.momoplan.pet.commons.handler;

import javax.servlet.http.HttpServletResponse;

import com.momoplan.pet.commons.bean.ClientRequest;

public interface RequestHandler {
	
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception;
	
}
