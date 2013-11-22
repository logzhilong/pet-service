package com.momoplan.pet.commons.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.momoplan.pet.commons.PetRequest;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ClientRequest extends PetRequest{
	String sn;
	String token;
	String service;
	String method;
	String mac;
	String imei;
	String channel;
	String version;

	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
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
