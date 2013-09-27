package com.momoplan.pet.vo;

import java.util.Date;

public class ReplyCommentViews {
	private long userStateid;
	private long replyid;
	private PetUserView replyUserView;
	private PetUserView commentUserView;
	private String commentsMsg;
	private Date commentTime;
	private int pageIndex;
	private long id;
	
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
	public PetUserView getReplyUserView() {
		return replyUserView;
	}
	public void setReplyUserView(PetUserView replyUserView) {
		this.replyUserView = replyUserView;
	}
	public PetUserView getCommentUserView() {
		return commentUserView;
	}
	public void setCommentUserView(PetUserView commentUserView) {
		this.commentUserView = commentUserView;
	}
	public long getReplyid() {
		return replyid;
	}
	public void setReplyid(long replyid) {
		this.replyid = replyid;
	}
	public String getCommentsMsg() {
		return commentsMsg;
	}
	public void setCommentsMsg(String commentsMsg) {
		this.commentsMsg = commentsMsg;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
}
