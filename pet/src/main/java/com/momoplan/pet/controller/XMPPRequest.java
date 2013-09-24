package com.momoplan.pet.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class XMPPRequest {
	private String words;
	private String sendUser;
	private String receiveUser;
	private String region;
	private String msgtype;
	private String prams;
	private Date msgTime;

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
				 + "\" " + " type = \"chat\" msgtype=" + "\"" + msgtype + "\" " + prams + " msgTime=" + "\"" + msgTime.getTime() + "\" " + "><body>" + this.words + "</body></message>";
		System.out.println(msg);
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://61.51.110.55:5280/rest");
		method.setRequestEntity(new StringRequestEntity(msg, "", "UTF-8"));
		httpClient.executeMethod(method);
	}
	
	public static void SendMessage(String msg) throws HttpException, IOException {
		System.out.println(msg);
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://61.51.110.55:5280/rest");
		method.setRequestEntity(new StringRequestEntity(msg, "", "UTF-8"));
		httpClient.executeMethod(method);
	}

//	public static void main(String[] args) {
//		 String restring=" //[[^@]]+//]";
//		 System.out.println("@test.com".matches("^@\\w+.com$"));
//		String msg = "<message to = " + "\"" + "123" + "@test.com" + "\" "
//				+ " from = " + "\"" + "321" + "@test.com" + "/rest" + "\" "
//				+ " type = " + "\"" + "reply" + "\" " + prams + "><body>"
//				+ this.words + "</body></message>";
//		System.out.println();
//	}
	public static void main(String[] args) {
		String msg = "<message xmlns=\"jabber:client\" from=\"15277880000@test.com\" to=\"15511487633@test.com\" type=\"chat\"><body>自言自语</body></message>";
		try {
			SendMessage(msg);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
