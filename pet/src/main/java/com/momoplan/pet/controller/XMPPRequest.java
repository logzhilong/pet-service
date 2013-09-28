package com.momoplan.pet.controller;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Value;

public class XMPPRequest {
	private String words;
	private String sendUser;
	private String receiveUser;
	private String region;
	private String msgtype;
	private String prams;
	private Date msgTime;
	private String fromNickname;
	private String fromHeadImg;
	private String xmpppath;
	
	public String getXmpppath() {
		return xmpppath;
	}

	public void setXmpppath(String xmpppath) {
		this.xmpppath = xmpppath;
	}
	
	public String getFromNickname() {
		return fromNickname;
	}

	public void setFromNickname(String fromNickname) {
		this.fromNickname = fromNickname;
	}

	public String getFromHeadImg() {
		return fromHeadImg;
	}

	public void setFromHeadImg(String fromHeadImg) {
		this.fromHeadImg = fromHeadImg;
	}

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	public String getType() {
		return msgtype;
	}

	public void setType(String type) {
		this.msgtype = type;
	}

	public String getPrams() {
		return prams;
	}
	
	// reply回复(Dynamicid),zanDynamic动态(Dynamicid)，zanPeople,zanPet(petid)
	public void setPrams(Long l) {
		if (this.msgtype.contains("reply") || this.msgtype.contains("zanDynamic")) {
			this.prams = "Dynamicid=\"" + l.toString()+"\"";
		}
		if (this.msgtype.contains("zanPeople")) {
		}
		if (this.msgtype.contains("zanPet")) {
			this.prams = "\"petid=\"" + l.toString()+"\"";
		}
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		if (region.matches("^@\\w+.com$")) {
			this.region = region;
		}
		this.region = "@test.com";
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public void SendMessage() throws HttpException, IOException { 
		String msg = "<message to = " + "\"" + this.receiveUser + this.region + "\" " + " from = " + "\"" + this.sendUser + this.region
				 + "\" " + " type = \"chat\" msgtype=" + "\"" + this.msgtype + "\" " + prams + " msgTime=" + "\"" + this.msgTime.getTime() + "\" " + " fromNickname=" + "\"" + this.fromNickname + "\" " + " fromHeadImg=" + "\"" + this.fromHeadImg + "\" " + "><body>" + this.words + "</body></message>";
		System.out.println(this.xmpppath);
		System.out.println(msg);
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(this.xmpppath);
		method.setRequestEntity(new StringRequestEntity(msg, "", "UTF-8"));
		httpClient.executeMethod(method);
	}
	
//	public static void SendMessage(String msg) throws HttpException, IOException {
//		System.out.println(msg);
//		HttpClient httpClient = new HttpClient();
//		PostMethod method = new PostMethod("http://61.51.110.55:5280/rest");
//		method.setRequestEntity(new StringRequestEntity(msg, "", "UTF-8"));
//		httpClient.executeMethod(method);
//	}

//	public static void main(String[] args) {
//		 String restring=" //[[^@]]+//]";
//		 System.out.println("@test.com".matches("^@\\w+.com$"));
//		String msg = "<message to = " + "\"" + "123" + "@test.com" + "\" "
//				+ " from = " + "\"" + "321" + "@test.com" + "/rest" + "\" "
//				+ " type = " + "\"" + "reply" + "\" " + prams + "><body>"
//				+ this.words + "</body></message>";
//		System.out.println();
//	}
//	public static void main(String[] args) {
//		XMPPRequest xr = new XMPPRequest();
//		xr.setFromHeadImg("1");
//		xr.setFromNickname("a");
//		xr.setMsgTime(new Date(System.currentTimeMillis()));
//		xr.setReceiveUser("b");
//		xr.setRegion("@test");
//		xr.setSendUser("A");
//		xr.setType("reply");
//		xr.setWords("XXX");
//		String msg = "<message to = " + "\"" + xr.receiveUser + xr.region + "\" " + " xr = " + "\"" + xr.sendUser + xr.region
//				 + "\" " + " type = \"chat\" msgtype=" + "\"" + xr.msgtype + "\" " + " msgTime=" + "\"" + xr.msgTime.getTime() + "\" " + " fromNickname=" + "\"" + xr.fromNickname + "\" " + " fromHeadImg=" + "\"" + xr.fromHeadImg + "\" " + "><body>" + xr.words + "</body></message>";
//		System.out.println(msg);
//	}
}
