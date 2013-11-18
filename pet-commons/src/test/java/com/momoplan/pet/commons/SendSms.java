package com.momoplan.pet.commons;

import com.momoplan.pet.commons.http.PostRequest;

public class SendSms {

	public static void main(String[] args) throws Exception {
		
		String url = "http://61.145.229.29:9002/MWGate/wmgw.asmx/MongateCsSpSendSmsNew";
		String userId = "J00348";//J00348
		String password = "142753";//142753
		String phoneNumber = "18612013831";
		
		String[] params = new String[]{
				"userId",userId,
				"password",password,
				"pszMobis",phoneNumber,
				"pszMsg","测试短信",
				"iMobiCount","1",
				"pszSubPort","***********"
		};
		System.out.println(MyGson.getInstance().toJson(params));
		String response = PostRequest.postText(url, params);	
		System.out.println("++++++++++++++++++++++++++++++++++++");
		System.out.println(response);
	}
	
}
