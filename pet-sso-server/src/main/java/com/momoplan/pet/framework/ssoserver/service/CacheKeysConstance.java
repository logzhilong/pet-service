package com.momoplan.pet.framework.ssoserver.service;

public interface CacheKeysConstance {
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";
	/**
	 * 列族名，存储 用户 TOKEN
	 */
	public static final String CF_TOKEN = "sso_server.cf.token";
	
}
