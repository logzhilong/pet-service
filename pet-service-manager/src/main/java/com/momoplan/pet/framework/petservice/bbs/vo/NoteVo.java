package com.momoplan.pet.framework.petservice.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.Note;

public class NoteVo extends Note{

	private static final long serialVersionUID = 1L;
	
	private String userName = null;
	
	private String nickname = null;
	
	/*
	 * 查询条件
	 */
	private String condition_state = "ALL";
	private String condition_isTop = "ALL";
	private String condition_isEute = "ALL";
	
	public String getCondition_state() {
		return condition_state;
	}

	public void setCondition_state(String condition_state) {
		this.condition_state = condition_state;
	}

	public String getCondition_isTop() {
		return condition_isTop;
	}

	public void setCondition_isTop(String condition_isTop) {
		this.condition_isTop = condition_isTop;
	}

	public String getCondition_isEute() {
		return condition_isEute;
	}

	public void setCondition_isEute(String condition_isEute) {
		this.condition_isEute = condition_isEute;
	}

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
