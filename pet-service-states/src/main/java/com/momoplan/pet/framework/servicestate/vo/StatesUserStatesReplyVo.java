package com.momoplan.pet.framework.servicestate.vo;

import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;

/**
 * 用户动态的回复信息
 * @author liangc
 *
 */
public class StatesUserStatesReplyVo extends StatesUserStatesReply {
	
	private static final long serialVersionUID = 1L;

	/** 用户别名 */
	private String alias = null;

	/** 用户昵称 */
	private String nickname = null;
	
	/** 用户名 */
	private String username = null;
	
	/** 用户头像 */
	private String userImage = null;

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
