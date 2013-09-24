package com.momoplan.pet.controller;

import com.momoplan.pet.domain.AuthenticationToken;
import com.momoplan.pet.domain.ChatServer;

public class LoginResponse {
	AuthenticationToken authenticationToken;
	ChatServer chatserver;
	public AuthenticationToken getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(AuthenticationToken authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
	public ChatServer getChatserver() {
		return chatserver;
	}
	public void setChatserver(ChatServer chatserver) {
		this.chatserver = chatserver;
	}
	
}
