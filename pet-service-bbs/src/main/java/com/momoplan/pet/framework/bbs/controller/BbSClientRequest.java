package com.momoplan.pet.framework.bbs.controller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.momoplan.pet.commons.ForumRequest;



@JsonIgnoreProperties(ignoreUnknown=true)
public class BbSClientRequest extends ForumRequest{
	String token;
	String method;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	
}
