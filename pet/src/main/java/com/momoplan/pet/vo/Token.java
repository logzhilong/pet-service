package com.momoplan.pet.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.momoplan.pet.domain.ChatServer;
@JsonIgnoreProperties(ignoreUnknown=true)
public class Token {
	
    private String token;

    private long expire;

    private long userid;

    private String createDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	

}
