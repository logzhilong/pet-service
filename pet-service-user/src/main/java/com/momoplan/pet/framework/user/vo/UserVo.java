package com.momoplan.pet.framework.user.vo;

import org.springframework.beans.BeanUtils;

import com.momoplan.pet.commons.domain.user.po.SsoUser;
/**
 * 用户信息，包括坐标
 * @author liangc
 */
public class UserVo extends SsoUser{
	
	public UserVo() {
		super();
	}
	
	public UserVo(SsoUser ssoUser) {
		super();
		BeanUtils.copyProperties(ssoUser, this);
	}
	
	public UserVo(SsoUser ssoUser,double longitude, double latitude) {
		super();
		BeanUtils.copyProperties(ssoUser, this);
		this.longitude = longitude;
		this.latitude = latitude;
	}


	private static final long serialVersionUID = 1L;

	private double longitude;
	
	private double latitude;

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
