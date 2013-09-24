package com.momoplan.pet.commons;

import java.util.UUID;

/**
 * 创建ID的工具
 * @author liangc
 */
public class IDCreater {
	
	/**
	 * 生成 32位 uuid
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
}
