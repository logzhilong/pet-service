package com.momoplan.pet.framework.petservice.report.vo;

import java.util.Date;

/**
 * 业务类型映射
 * @author liangc
 */
public class ServiceDict {
	
	private String title = null;
	
	private String service = null;
	
	private String method = null;
	
	private Date ct = null;
	
	private Date et = null;
	
	public Date getEt() {
		return et;
	}

	public void setEt(Date et) {
		this.et = et;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
