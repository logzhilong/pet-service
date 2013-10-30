package com.momoplan.pet.commons.repository.user;

public interface CacheKeysConstance {
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";
	/**
	 * 搜索用户的索引,单条件模糊搜索，SEARCH_USER_INDEX + userid + ":" + nickname + phonenumber = userid
	 * 每个用户在这里，就只能有一条记录，通过 key 来模糊查找 userid
	 */
	public static final String SEARCH_USER_INDEX = "sso_server.kv.sso_user_index.search:";
	
	/**
	 * 列族名，存储 用户 TOKEN
	 */
	public static final String CF_TOKEN = "sso_server.cf.token";
	/**
	 * 随机码 xcode 存储，后面加上 phoneNumber 每个值有效期为 10分钟
	 */
	public static final String CF_XCODE = "sso_server.xcode:";
	
}
