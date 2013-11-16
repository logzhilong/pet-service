package com.momoplan.pet.framework.petservice.customer.vo;

import com.momoplan.pet.commons.domain.user.po.SsoUser;

public class SsoUserVo extends SsoUser{
	
	private static final long serialVersionUID = 1L;

	private String imgUrl = null;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
