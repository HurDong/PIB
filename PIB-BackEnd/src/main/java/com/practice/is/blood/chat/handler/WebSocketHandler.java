package com.practice.is.blood.chat.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.is.blood.chat.domain.Message;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Set<String> usernames = new HashSet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		System.out.println("Connected: " + session.getId()); // 세션 ID 출력

		// 클라이언트에게 세션 ID 전송
		session.sendMessage(new TextMessage("{\"type\":\"session_id\", \"id\":\"" + session.getId() + "\"}"));

		// 현재 연결된 모든 세션 ID 출력
		System.out.println("Current connected sessions:");
		for (WebSocketSession s : sessions) {
			System.out.println("Session ID: " + s.getId());
		}
	}

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        try {
//            String payload = message.getPayload();
//            Message chatMessage = objectMapper.readValue(payload, Message.class);
//
//            if ("join".equals(chatMessage.getType())) {
//                usernames.add(chatMessage.getUsername());
//                broadcastUserList();
//            } else if ("leave".equals(chatMessage.getType())) {
//                usernames.remove(chatMessage.getUsername());
//                broadcastUserList();
//            } else if ("message".equals(chatMessage.getType()) || "offer".equals(chatMessage.getType())
//                    || "answer".equals(chatMessage.getType()) || "candidate".equals(chatMessage.getType())) {
//                chatMessage.setTimestamp(Instant.now().toString());
//                for (WebSocketSession s : sessions) {
//                    if (s.isOpen() && !session.getId().equals(s.getId())) {
//                        s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error handling message: " + e.getMessage());
//            e.printStackTrace();
//            session.close(CloseStatus.SERVER_ERROR);
//        }
//    }
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		for (WebSocketSession webSocketSession : session.getOpenSessions()) {
			if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
				webSocketSession.sendMessage(message);
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		broadcastUserList();
		System.out.println("Disconnected: " + session.getId()); // 세션 ID 출력

		// 현재 연결된 모든 세션 ID 출력
		System.out.println("Current connected sessions:");
		for (WebSocketSession s : sessions) {
			System.out.println("Session ID: " + s.getId());
		}
	}

	private void broadcastUserList() throws Exception {
		Message userMessage = new Message();
		userMessage.setType("users");
		userMessage.setUsers(new HashSet<>(usernames));

		for (WebSocketSession s : sessions) {
			if (s.isOpen()) {
				s.sendMessage(new TextMessage(objectMapper.writeValueAsString(userMessage)));
			}
		}
	}
}
