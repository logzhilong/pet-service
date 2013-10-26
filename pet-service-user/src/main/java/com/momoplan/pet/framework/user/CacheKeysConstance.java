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
	/**
	 * TODO : 这个数据，需要人工干预初始化
	 * 用户ID和宠物类型的联合索引，这个索引建在这就需要有初始化的功能了
	 * USERID_PETTYPE_INDEX + userId + ":" + petType + ":" petId
	 */
	public static final String USERID_PETTYPE_INDEX = "service_user.userid_pettype.index:";
	
	/**
	 * 用于存储用户的好友信息，格式为 FRIEND_KEY + friendShipId + ":" + (userA+userB)
	 * 查找时，匹配 *userA* 或者 *userB* 来找到好友
	 * 值 存储 {"aid":"","bid":""}
 	 */
	public static final String FRIEND_KEY = "service_user.friend:";
	
}
