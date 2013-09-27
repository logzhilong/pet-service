package com.momoplan.pet.vo;

import java.util.Date;
import java.util.List;

public class ReplyView {
	private long id;
	private long userStateid;
	private String msg;
	private Date replyTime;
	private PetUserView petUserView;
	private List<ReplyCommentViews> replyCommentViews;
	private int pageIndex;
	private long countReplyComment;
	
	public long getCountReplyComment() {
		return countReplyComment;
	}
	public void setCountReplyComment(long countReplyComment) {
		this.countReplyComment = countReplyComment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public long getUserStateid() {
		return userStateid;
	}
	public void setUserStateid(long userStateid) {
		this.userStateid = userStateid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public List<ReplyCommentViews> getReplyCommentViews() {
		return replyCommentViews;
	}
	public void setReplyCommentViews(List<ReplyCommentViews> replyCommentViews) {
		this.replyCommentViews = replyCommentViews;
	}
	public PetUserView getPetUserView() {
		return petUserView;
	}
	public void setPetUserView(PetUserView petUserView) {
		this.petUserView = petUserView;
	}
}
