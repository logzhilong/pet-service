package com.momoplan.pet.framework.ssoserver.service;

public interface SsoUserIndexConstance {
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "cf.sso_user_index.username";
	/**
	 * 列族名，存储 用户 TOKEN
	 */
	public static final String CF_TOKEN = "cf.token";
	
}
