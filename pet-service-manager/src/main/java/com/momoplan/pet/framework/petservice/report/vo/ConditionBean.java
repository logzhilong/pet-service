package com.momoplan.pet.framework.petservice.report.vo;

public class ConditionBean {
	
	public static String serviceMethodSplit = "__";
	
	private String serviceMethod = null;
	
	private String channel = null;
	
	private String month = null;
	
	private String cd = null;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

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
