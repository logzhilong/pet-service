package com.momoplan.pet.framework.petservice.report.vo;

public class ConditionBean {
	
	public static String serviceMethodSplit = "__";
	
	private String serviceMethod = null;
	
	private String month = null;
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	
}
