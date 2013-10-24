package com.momoplan.pet.framework.user.vo;

import java.util.ArrayList;
import java.util.List;

public class NearPerson {
	
	private UserVo user ;
	
	private List<PetVo> petList = new ArrayList<PetVo>();
	
	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public List<PetVo> getPetList() {
		return petList;
	}

	public void setPetList(List<PetVo> petList) {
		this.petList = petList;
	}
	
}
