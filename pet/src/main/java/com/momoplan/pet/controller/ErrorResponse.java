package com.momoplan.pet.controller;

public class ErrorResponse {
	private String errorcode;
	private String errorMessage;
	
	public ErrorResponse(String errorcode, String errorMessage) {
		super();
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
