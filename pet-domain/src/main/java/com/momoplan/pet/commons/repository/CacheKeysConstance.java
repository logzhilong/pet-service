package com.momoplan.pet.commons.repository;

public interface CacheKeysConstance {

	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * BBS
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 */
	/**
	 * 这个列族的KEY 是变化的 其结构为 list ，KEY 结尾是一个主题的ID，内容是主题的全部回复信息
	 * 即可以通过一个 note.id 得到一个回帖的列表，按照时间倒序
	 */
	public static final String CF_NOTE = null;
	/**
	 * 栏目的发帖总数，key后面加上 forumId
	 */
	public static final String LIST_NOTE_TOTALCOUNT = "service_bbs.list.note.totalCount:";
	/**
	 * 栏目当天的发帖数量,key后面加上 forumId:yyyy-MM-dd
	 */
	public static final String LIST_NOTE_TOTALTODAY = "service_bbs.list.note.totalToday:";
	/**
	 * 栏目的帖子缓存，key后面加上 forumId，缓存的帖子是【堆栈】结构
	 */
	public static final String LIST_NOTE = "service_bbs.list.note:";
	/**
	 * 每个栏目的回帖总数,key后面要加上 forumId
	 */
	public static final String LIST_NOTE_SUB_TOTALCOUNT = "service_bbs.list.note_sub.totalCount:";
	/**
	 * 每个主题的回帖缓存, key后面要加上 noteId，缓存的回帖是【队列】结构
	 */
	public static final String LIST_NOTE_SUB = "service_bbs.list.note_sub:";
	/**
	 * 置顶帖子的缓存，LIST_NOTE_TOP + fid
	 */
	public static final String LIST_NOTE_TOP = "service_bbs.list.note_top:";
	/**
	 * 帖子的点击次数，LIST_NOTE_CLICK_COUNT + noteId
	 */
	public static final String LIST_NOTE_CLICK_COUNT = "service_bbs.list.note_click_count:";
	/**
	 * 用户回帖的记录,hash存储结构可以排除重复记录， USER_REPLY_NOTE + userId
	 */
	public static final String USER_REPLY_NOTE = "service_bbs.hash.user_reply_note:";	
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * PAT
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 */
	/**
	 * 缓存KEY可以做成 key=title:userid:srcid:patid
	 */
	public static final String USER_PAT = "service_pat.user_src_pat:";

	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * STATES
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 */
	/**
	 * 用户的动态列表，根据用户ID来获取, key = LIST_USER_STATES + userId
	 */
	public static final String HASH_USER_STATES = "service_states.hash.user_states:";
	
	/**
	 * 动态的回复列表，根据动态的ID来获取, key = LIST_USER_STATES_REPLY + statesId
	 */
	public static final String HASH_USER_STATES_REPLY = "service_states.hash.user_states_reply:";
	
	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * USER 域使用
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 */
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	//public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";
	/**
	 * 搜索用户的索引,单条件模糊搜索，SEARCH_USER_INDEX + userid + ":" + username + ":" + nickname = userid
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