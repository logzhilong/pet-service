package com.momoplan.common;

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PushNotificationPayload;

import com.momoplan.pet.vo.ApnMsg;

public class PushApn {

	/**
	 * 单发消息
	 * @param msg
	 * @param count
	 * @param token
	 */
	public static void sendMsgApn(ApnMsg msg,int count){
		if(null==msg.getToken()||""==msg.getToken()){
			return;
		}
		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(msg.getMsg()); // 消息内容
			payLoad.addBadge(count); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			Device device = new BasicDevice();
			device.setToken(msg.getToken());
			String cert = System.getProperty("user.home")+"/.ssh/pushCertTest.p12";
			Push.payload(payLoad, cert, "110110", false, device);
//			String cert = System.getProperty("user.home")+"/.ssh/pushCert.p12";
//			Push.payload(payLoad, cert, "110110", true, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 广播消息给指定用户(多线程推送)
	 * @param msg
	 * @param count
	 * @param tokens
	 */
	public static void sendMsgsApn(String msg,List<String> tokens) {
		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(msg); // 消息内容
			payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
			payLoad.addSound("default"); // 铃音 默认
			List<Device> devices = new ArrayList<Device>();
			for (String token : tokens) {
				Device device = new BasicDevice();
				device.setToken(token);
				devices.add(device);
			}
			/* 指定需要多少线程发送 */
			int threads = 30;
			/* 启动线程并发送 */
			String cert = System.getProperty("user.home")+"/.ssh/pushCert.p12";
//			System.out.println(file.replace("/", "\\"));
//			System.out.println(cert);
			Push.payload(payLoad, cert , "110110", false, threads,devices);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args){
//		String msg = "test";
//		List<String> tokens = new ArrayList<String>();
//		tokens.add("684c474512603e53d5aa719fb2e135f313c89f0759e80f277e01498adf5010e4");
//		tokens.add("263584ada5adcceba50c74b5802103c8cf36c481944cf4a5a2a49a858bb8bec8");
//		sendMsgsApn(msg,tokens);
//		sendMsgApn(new ApnMsg("test1","263584ada5adcceba50c74b5802103c8cf36c481944cf4a5a2a49a858bb8bec8"),111);
//	}
	
}
