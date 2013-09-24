package com.momoplan.pet.vo;

public class ApnMsg {
	private String msg;
	private String token;
	
	public ApnMsg(String msg,String token){
		this.setMsg(msg);
		this.setToken(token);
	}
	public ApnMsg(){
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
