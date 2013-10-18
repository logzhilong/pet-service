package com.momoplan.pet.framework.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.Note;

public class NoteVo extends Note{
	/**
	 * 昵称
	 */
	private String nickname = null;
	/**
	 * 头像
	 */
	private String userIcon = null;
	/**
	 * 回帖数
	 */
	private Long totalReply = 0L;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Long getTotalReply() {
		return totalReply;
	}

	public void setTotalReply(Long totalReply) {
		this.totalReply = totalReply;
	}

}
