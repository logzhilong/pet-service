package com.momoplan.pet.framework.base.vo;

import java.util.Date;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;

public class MgrTrustUserVo extends MgrTrustUser {

	private static final long serialVersionUID = 1L;
	
	private String userName = null;
	
	private String nickname = null;
	
	private String img = null;
	
	private Date ct = null;
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
