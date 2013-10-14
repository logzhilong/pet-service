package com.momoplan.pet.framework.bbs.service;

public interface BbsNoteCountService {
	/**
	 * 某圈子今日新增帖子数
	 */
	public String getNewNoteNumByForumid(String fid);
	/**
	 * 某圈子所有帖子数
	 */
	public String getNoteNumByForumid(String fid);
	/**
	 * 某圈子所有回复数
	 */
	public String getNoteRelNumByForumid(String fid);
}

