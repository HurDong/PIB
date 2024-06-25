package com.practice.is.blood.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.practice.is.blood.chat.domain.ChatMessage;

@Controller
public class ChatController {

//	@MessageMapping("/chat.sendMessage")
//	@SendTo("/topic/public")
//	public ChatMessage sendMessage(ChatMessage chatMessage) {
//		return chatMessage;
//	}
//
//	@MessageMapping("/chat.addUser")
//	@SendTo("/topic/public")
//	public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//		return chatMessage;
//	}
}
