package com.practice.is.blood.chat.handler;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<String> usernames = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Message chatMessage = objectMapper.readValue(payload, Message.class);

        if ("join".equals(chatMessage.getType())) {
            usernames.add(chatMessage.getUsername());
            broadcastUserList();
        } else if ("leave".equals(chatMessage.getType())) {
            usernames.remove(chatMessage.getUsername());
            broadcastUserList();
        } else if ("message".equals(chatMessage.getType())) {
            chatMessage.setTimestamp(Instant.now().toString());
            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        broadcastUserList();
        System.out.println("Disconnected: " + session.getId());
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

    private static class Message {
        private String type;
        private String username;
        private String text;
        private String timestamp;
        private Set<String> users;

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Set<String> getUsers() {
            return users;
        }

        public void setUsers(Set<String> users) {
            this.users = users;
        }
    }
}
