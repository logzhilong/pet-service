package com.momoplan.pet.framework.statistic.vo;

import java.util.HashMap;

public class PetComRequest {
	String method;
	
	HashMap<String, Object> params;

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
