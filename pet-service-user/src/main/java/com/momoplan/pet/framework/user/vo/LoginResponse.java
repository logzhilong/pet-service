package com.momoplan.pet.framework.user.vo;

import java.io.Serializable;

import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoChatServer;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SsoAuthenticationToken authenticationToken;
	private SsoChatServer chatserver;
	
	public LoginResponse() {
		super();
	}

	public LoginResponse(SsoAuthenticationToken authenticationToken, SsoChatServer chatserver) {
		super();
		this.authenticationToken = authenticationToken;
		this.chatserver = chatserver;
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
