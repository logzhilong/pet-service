package com.momoplan.pet.vo;

import java.util.Date;

public class PetStisticJmsView {
	private long Userid;
	private Date connectTime;
	public long getUserid() {
		return Userid;
	}
	public void setUserid(long userid) {
		Userid = userid;
	}
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
	
}
