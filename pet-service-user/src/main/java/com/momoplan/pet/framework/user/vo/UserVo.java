package com.momoplan.pet.framework.user.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import com.momoplan.pet.commons.domain.user.po.SsoUser;

/**
 * 用户信息，包括坐标
 * 
 * @author liangc
 */
public class UserVo extends SsoUser {

	public UserVo() {
		super();
	}

	public UserVo(SsoUser ssoUser) {
		super();
		BeanUtils.copyProperties(ssoUser, this);
	}

	public UserVo(SsoUser ssoUser, String longitude, String latitude) {
		super();
		BeanUtils.copyProperties(ssoUser, this);
		this.longitude = longitude;
		this.latitude = latitude;
	}

	private static final long serialVersionUID = 1L;

	// 别名
	private String alias;

	private String longitude;

	private String latitude;
	//距离
	private String distance;
	
	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
