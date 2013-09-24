package com.momoplan.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momoplan.pet.domain.ChatServer;

@Service
public class ChatServerService {
	
	public ChatServer getAvailableServer(long userid){
		List<ChatServer> findAllChatServers = ChatServer.findAllChatServers();
		if(findAllChatServers.size()<=0){
			return null;
		}
		return ChatServer.findAllChatServers().get(0);
	}
}
