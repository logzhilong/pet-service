package com.momoplan.pet.framework.ssoserver.vo;

import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoChatServer;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;

public class LoginResponse {
	private SsoAuthenticationToken authenticationToken;
	private SsoChatServer chatserver;
	private SsoVersion version ;
	private String firstImage ;
	private String verifyCode ;
	
	public LoginResponse(SsoAuthenticationToken authenticationToken, SsoChatServer chatserver) {
		super();
		this.authenticationToken = authenticationToken;
		this.chatserver = chatserver;
	}

	public LoginResponse(SsoVersion version, String firstImage) {
		super();
		this.version = version;
		this.firstImage = firstImage;
	}

	public LoginResponse(SsoAuthenticationToken authenticationToken, SsoChatServer chatserver, SsoVersion version, String firstImage) {
		super();
		this.authenticationToken = authenticationToken;
		this.chatserver = chatserver;
		this.version = version;
		this.firstImage = firstImage;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public SsoVersion getVersion() {
		return version;
	}
	public void setVersion(SsoVersion version) {
		this.version = version;
	}
	public SsoAuthenticationToken getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(SsoAuthenticationToken authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	public SsoChatServer getChatserver() {
		return chatserver;
	}
	public void setChatserver(SsoChatServer chatserver) {
		this.chatserver = chatserver;
	}
	
}
