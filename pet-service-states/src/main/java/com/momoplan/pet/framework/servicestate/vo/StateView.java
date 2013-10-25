package com.momoplan.pet.framework.servicestate.vo;

import java.util.Date;
import java.util.List;

import com.momoplan.pet.commons.domain.pat.po.PatUserPat;

public class StateView {
	private String id;//主键
	private Boolean ifTransmitMsg;//是否为转发0是1否2收藏
	private String transmitUrl;//转发链接
	private String transmitMsg;//转发附带信息
	private String imgid;//图片信息
	private String msg;//消息体
//	private String distance;//距离
	private Date ct;//创建时间
	private String state;//动态的状态0正常，1假动态，2置顶动态，3审核中，4未通过，5被举报
	private PetUserView petUserView;//用户视图
//	private int pageIndex;//页码
//	private List<ReplyView> replyViews;//回复视图
	private int countZan;//统计赞的次数
	private List<UserZan> patUserPat;//赞的视图
	private Boolean ifIZaned;//是否已经赞过
//	private int countReplys;//统计总回复数量,根据关系计算
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIfTransmitMsg() {
		return ifTransmitMsg;
	}
	public void setIfTransmitMsg(Boolean ifTransmitMsg) {
		this.ifTransmitMsg = ifTransmitMsg;
	}
	public String getTransmitUrl() {
		return transmitUrl;
	}
	public void setTransmitUrl(String transmitUrl) {
		this.transmitUrl = transmitUrl;
	}
	public String getTransmitMsg() {
		return transmitMsg;
	}
	public void setTransmitMsg(String transmitMsg) {
		this.transmitMsg = transmitMsg;
	}
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
//	public String getDistance() {
//		return distance;
//	}
//	public void setDistance(String distance) {
//		this.distance = distance;
//	}
	public Date getCt() {
		return ct;
	}
	public void setCt(Date ct) {
		this.ct = ct;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public PetUserView getPetUserView() {
		return petUserView;
	}
	public void setPetUserView(PetUserView petUserView) {
		this.petUserView = petUserView;
	}
//	public int getPageIndex() {
//		return pageIndex;
//	}
//	public void setPageIndex(int pageIndex) {
//		this.pageIndex = pageIndex;
//	}
//	public List<ReplyView> getReplyViews() {
//		return replyViews;
//	}
//	public void setReplyViews(List<ReplyView> replyViews) {
//		this.replyViews = replyViews;
//	}
	public Boolean getIfIZaned() {
		return ifIZaned;
	}
	public List<UserZan> getPatUserPat() {
		return patUserPat;
	}
	public void setPatUserPat(List<UserZan> patUserPat) {
		this.patUserPat = patUserPat;
	}
	public void setIfIZaned(Boolean ifIZaned) {
		this.ifIZaned = ifIZaned;
	}
	public int getCountZan() {
		return countZan;
	}
	public void setCountZan(int countZan) {
		this.countZan = countZan;
	}
//	public int getCountReplys() {
//		return countReplys;
//	}
//	public void setCountReplys(int countReplys) {
//		this.countReplys = countReplys;
//	}
	
}
