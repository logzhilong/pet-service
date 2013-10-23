package com.momoplan.pet.framework.servicestate;


public interface CacheKeysConstance {
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";
	/**
	 * 列族名，存储 用户 TOKEN
	 */
	public static final String CF_TOKEN = "sso_server.cf.token";
	/**
	 * 随机码 xcode 存储，后面加上 phoneNumber 每个值有效期为 10分钟
	 */
	public static final String CF_XCODE = "sso_server.xcode:";
	
}
