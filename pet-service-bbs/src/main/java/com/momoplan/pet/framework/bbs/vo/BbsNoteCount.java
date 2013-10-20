package com.momoplan.pet.framework.bbs.vo;

public class BbsNoteCount {
	private String id;
	//图片
	private String imgId;
	// 名字
	private String name;
	// 是否关注
	private String isattention;
	//圈子当天总帖数
	private Long totalToday = 0L;
	//圈子的总帖子数
	private Long totalCount = 0L;
	//圈子的总回复数
	private Long totalReply = 0L;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsattention() {
		return isattention;
	}
	public void setIsattention(String isattention) {
		this.isattention = isattention;
	}
	public Long getTotalToday() {
		return totalToday;
	}
	public void setTotalToday(Long totalToday) {
		this.totalToday = totalToday;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getTotalReply() {
		return totalReply;
	}
	public void setTotalReply(Long totalReply) {
		this.totalReply = totalReply;
	}		

}
