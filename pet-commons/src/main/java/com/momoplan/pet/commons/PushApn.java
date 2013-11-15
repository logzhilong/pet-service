package com.momoplan.pet.commons;

import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PushNotificationPayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 给 IOS 推送消息
 * @author liangc
 */
public class PushApn {
	
	private static Logger logger = LoggerFactory.getLogger(PushApn.class);
	
	/**
	 * 单发消息
	 * @param deviceToken 
	 * @param msg 
	 * @param pwd 密码 110110
	 */
	public static void sendMsgApn(String deviceToken,String msg,String pwd,boolean debug) throws Exception {
		try {
			String cert = System.getProperty("user.home")+"/.ssh/pushCert.p12";
			logger.debug("cert="+cert);
			logger.debug("deviceToken="+deviceToken+" ; pwd="+pwd+" ; debug="+debug+" ; msg="+msg); 
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addSound("default"); // 铃音 默认
			payLoad.addAlert(msg);
			Device device = new BasicDevice();
			device.setToken(deviceToken);
			Push.payload(payLoad, cert, pwd , !debug , device);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String token = "c2d3da17bcfdeb134768c462dbe3ff36b6a54e57214b732b74780d6dfb04c1ed";
		sendMsgApn(token,"XXXXXXXXXXXX测试-TRUE","110110",false);
		System.out.println("OK...");
	}
	
}
