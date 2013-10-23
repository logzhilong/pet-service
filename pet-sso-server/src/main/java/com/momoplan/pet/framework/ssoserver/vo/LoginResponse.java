package com.momoplan.pet.framework.ssoserver.vo;

import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoChatServer;

public class LoginResponse {
	private SsoAuthenticationToken authenticationToken;
	private SsoChatServer chatserver;
	
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
