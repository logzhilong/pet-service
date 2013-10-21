package com.momoplan.pet.commons.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.momoplan.pet.commons.MyGson;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class Success {
	
	private boolean success = true;
	
	private String entity = null;

	public Success() {
		super();
	}

	public Success(boolean success, String entity) {
		super();
		this.success = success;
		this.entity = entity;
	}
	
	public Success(boolean success, Object entity) {
		super();
		this.success = success;
		this.entity = MyGson.getInstance().toJson(entity);
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public void setEntity(Object _entity) {
		this.entity = MyGson.getInstance().toJson(_entity);
	}
	
	@Override
	public String toString() {
		return MyGson.getInstance().toJson(this);
	}
	
}
