package com.momoplan.pet.framework.petservice.report.vo;

public class ConditionBean {
	
	public static String serviceMethodSplit = "__";
	
	private String serviceMethod = null;
	
	private String channel = null;
	
	private String month = null;
	
	private String cd = null;
	/**
	 * 排序字段，默认按照 new_user 排序
	 */
	private String orderField = "new_user";
	/**
	 * asc / desc
	 */
	private String orderDirection = "desc";
	
	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

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
