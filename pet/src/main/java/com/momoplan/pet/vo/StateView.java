package com.momoplan.pet.vo;

import java.util.Date;
import java.util.List;

import com.momoplan.pet.domain.UserZan;

public class StateView implements Comparable<StateView>{
	private long id;
	private String transmitMsg;
	private String transmitUrl;
	private String ifTransmitMsg;
	private Date submitTime;
	private String msg;
	private String imgid;
	private String distance;
	private PetUserView petUserView;
	private int pageIndex;
	private List<ReplyView> replyViews;
	private long countZan;
	private List<UserZan> userZan;
	private String ifIZaned;
	private long countReplys;
	private String stateType;
	
	
	public List<UserZan> getUserZan() {
		return userZan;
	}
	public void setUserZan(List<UserZan> userZan) {
		this.userZan = userZan;
	}
	public String getStateType() {
		return stateType;
	}
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	public PetUserView getPetUserView() {
		return petUserView;
	}
	public void setPetUserView(PetUserView petUserView) {
		this.petUserView = petUserView;
	}
	public long getCountReplys() {
		return countReplys;
	}
	public void setCountReplys(long countReplys) {
		this.countReplys = countReplys;
	}
	public String getIfIZaned() {
		return ifIZaned;
	}
	public void setIfIZaned(String ifIZaned) {
		this.ifIZaned = ifIZaned;
	}
	public long getCountZan() {
		return countZan;
	}
	public void setCountZan(long countZan) {
		this.countZan = countZan;
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
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTransmitMsg() {
		return transmitMsg;
	}
	public void setTransmitMsg(String transmitMsg) {
		this.transmitMsg = transmitMsg;
	}
	public String getTransmitUrl() {
		return transmitUrl;
	}
	public void setTransmitUrl(String transmitUrl) {
		this.transmitUrl = transmitUrl;
	}
	public String getIfTransmitMsg() {
		return ifTransmitMsg;
	}
	public void setIfTransmitMsg(String ifTransmitMsg) {
		this.ifTransmitMsg = ifTransmitMsg;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public List<ReplyView> getReplyViews() {
		return replyViews;
	}
	public void setReplyViews(List<ReplyView> replyViews) {
		this.replyViews = replyViews;
	}
	
	@Override
	public int compareTo(StateView arg0) {
		if(this.submitTime.before(arg0.submitTime)){
			return 1;
		}
		if(arg0.submitTime.before(this.submitTime)){
			return -1;
		}
        return 0;
	}
}
