package com.momoplan.pet.framework.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.NoteSub;

@SuppressWarnings("serial")
public class NoteSubVo extends NoteSub{
	/**
	 * 昵称
	 */
	private String nickname = null;
	/**
	 * 头像
	 */
	private String userIcon = null;

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

}
