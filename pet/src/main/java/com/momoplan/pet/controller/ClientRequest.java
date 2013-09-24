package com.momoplan.pet.controller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.momoplan.common.PetRequest;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ClientRequest extends PetRequest{
	String token;
	String method;
	String mac;
	String imei;
	String channel;
	String version;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
}
