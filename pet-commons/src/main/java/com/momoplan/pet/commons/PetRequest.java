package com.momoplan.pet.commons;

import java.util.HashMap;

public class PetRequest {
	HashMap<String, Object> params;
	private String method;
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
}
