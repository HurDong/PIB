package com.practice.is.blood.chat.domain;

import java.util.Set;

import lombok.Data;

@Data
public class Message {
	private String type;
	private String username;
	private String text;
	private String timestamp;
	private Set<String> users;
	private Object sdp; // sdp 필드 추가
	private Object candidate; // candidate 필드 추가
}
