package com.momoplan.pet.framework.petservice.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.Note;

public class NoteVo extends Note{

	private static final long serialVersionUID = 1L;
	
	private String userName = null;
	
	private String nickname = null;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
