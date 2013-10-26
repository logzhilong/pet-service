package com.momoplan.pet.framework.servicestate.vo;

import com.momoplan.pet.commons.domain.states.po.StatesUserStates;

/**
 * 用户动态信息
 * @author liangc
 */
public class StatesUserStatesVo extends StatesUserStates {

	private static final long serialVersionUID = 1L;
	
//	{"id":"747","alias":"别名","nickname":"cc","username":"cc","phoneNumber":"","deviceToken":""}
	
	/** 用户别名 */
	private String alias = null;

	/** 用户昵称 */
	private String nickname = null;
	
	/** 用户名 */
	private String username = null;
	
	/** 用户头像 */
	private String userImage = null;
	/**
	 * 赞的总数
	 */
	private String totalPat = null;
	/**
	 * 我是不是赞过，true 是 ，false 否 
	 */
	private boolean didIpat = false;
	
	public String getTotalPat() {
		return totalPat;
	}

	public void setTotalPat(String totalPat) {
		this.totalPat = totalPat;
	}

	public boolean isDidIpat() {
		return didIpat;
	}

	public void setDidIpat(boolean didIpat) {
		this.didIpat = didIpat;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	
}
