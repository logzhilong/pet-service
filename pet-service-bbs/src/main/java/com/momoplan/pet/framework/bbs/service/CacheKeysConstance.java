package com.momoplan.pet.framework.bbs.service;


public interface CacheKeysConstance {
	/**
	 * 列族名，存储 用户表 username 列的索引
	 */
	public static final String CF_INDEX_USER_USERNAME = "sso_server.cf.sso_user_index.username";
	
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
	 * 帖子的举报记录， HASH_NOTE_REPORT + noteId
	 */
	public static final String HASH_NOTE_REPORT = "service_bbs.hash.note_report:";

	/**
	 * 帖子回复的举报记录， HASH_REPLY_REPORT + replyId
	 */
	public static final String HASH_REPLY_REPORT = "service_bbs.hash.reply_report:";

}
