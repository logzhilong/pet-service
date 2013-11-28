package com.momoplan.pet.framework.petservice.albums.vo;

import java.util.ArrayList;
import java.util.List;

import com.momoplan.pet.commons.domain.albums.po.Photos;

public class PhotosVo extends Photos {

	private static final long serialVersionUID = 1L;

	private String action = "ADD";
	
	private List<String> ids = new ArrayList<String>();
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
