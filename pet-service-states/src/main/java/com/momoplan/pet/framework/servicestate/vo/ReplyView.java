package com.momoplan.pet.framework.servicestate.vo;

import java.util.Date;

public class ReplyView {
	private String id;
	private String stateid;
	private String pid;
	private String msg;
	private String userid;
	private String puserid;
	private Date ct;
	private PetUserView petUserView;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStateid() {
		return stateid;
	}
	public void setStateid(String stateid) {
		this.stateid = stateid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPuserid() {
		return puserid;
	}
	public void setPuserid(String puserid) {
		this.puserid = puserid;
	}
	public Date getCt() {
		return ct;
	}
	public void setCt(Date ct) {
		this.ct = ct;
	}
	public PetUserView getPetUserView() {
		return petUserView;
	}
	public void setPetUserView(PetUserView petUserView) {
		this.petUserView = petUserView;
	}
	
}
