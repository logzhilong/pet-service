package com.momoplan.pet.framework.statistic.vo;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ClientRequest extends PetComRequest{
	String token;
	String method;
	String mac;
	String imei;
	String channel;
	String service;
	String connectTime;
	

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(String connectTime) {
		this.connectTime = connectTime;
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
