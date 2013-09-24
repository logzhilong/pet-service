package com.momoplan.pet.controller;

import java.util.ArrayList;
import java.util.List;

import com.momoplan.pet.domain.ChatServer;
import com.momoplan.pet.domain.PetVersion;
import com.momoplan.pet.vo.PetUserView;

public class OpenResponse {
	PetUserView petUserView;
	boolean needUpdate;//是否需要更新
	boolean forceUpdate;//是否强制更新
	ChatServer chatserver;
	String token;
	String iosurl;
	
	List<PetVersion> updatefiles=new ArrayList<PetVersion>();
	
	public List<PetVersion> getUpdatefiles() {
		return updatefiles;
	}
	public void setUpdatefiles(List<PetVersion> updatefiles) {
		this.updatefiles = updatefiles;
	}
	public PetUserView getPetUserView() {
		return petUserView;
	}
	public void setPetUserView(PetUserView petUserView) {
		this.petUserView = petUserView;
	}
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	public boolean isForceUpdate() {
		return forceUpdate;
	}
	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ChatServer getChatserver() {
		return chatserver;
	}
	public void setChatserver(ChatServer chatserver) {
		this.chatserver = chatserver;
	}
	public String getIosurl() {
		return iosurl;
	}
	public void setIosurl(String iosurl) {
		this.iosurl = iosurl;
	}
}
