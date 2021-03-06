package com.momoplan.pet.framework.bbs.vo;

import java.util.ArrayList;
import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.Note;

@SuppressWarnings("serial")
public class NoteVo extends Note{
	/**
	 * 分类名称
	 */
	private String assortName = null;
	/**
	 * 昵称
	 */
	private String nickname = null;
	
	/**
	 * 131210 : 圈子名称
	 */
	private String forumName = null;
	
	/**
	 * 头像
	 */
	private String userIcon = null;
	/**
	 * creater total reply
	 */
	private Long cTotalReply = 0L;
	/**
	 * 回帖数
	 */
	private Long totalReply = 0L;
	
	private List<String> images = new ArrayList<String>();
	
	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Long getcTotalReply() {
		return cTotalReply;
	}

	public void setcTotalReply(Long cTotalReply) {
		this.cTotalReply = cTotalReply;
	}

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

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getAssortName() {
		return assortName;
	}

	public void setAssortName(String assortName) {
		this.assortName = assortName;
	}
}
