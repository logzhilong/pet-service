package com.momoplan.pet.framework.user;

public interface CacheKeysConstance {
	
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";

	/**
	 * 用户坐标，这里会存储坐标轨迹 LIST_USER_LOCATION + userid
	 */
	public static final String LIST_USER_LOCATION = "service_user.list.user_location:";

	/**
	 * 用户的坐标 geoHash 前缀， USER_LOCATION_GEOHASH + userid + : + hashcode
	 */
	public static final String USER_LOCATION_GEOHASH = "user_location.geohash:";

	/**
	 * 附近的人的缓冲区前缀，每个用户都会有一个缓冲区，例如 LIST_USER_NEAR_BUFFER + userid
	 * 缓冲区主要为了完成翻页操作，当前人如果查看附近时，传递了 pageIndex=1 那么就要重建这个人的缓冲区
	 */
	public static final String LIST_USER_NEAR_BUFFER = "service_user.list.near.buffer:";
	
}
