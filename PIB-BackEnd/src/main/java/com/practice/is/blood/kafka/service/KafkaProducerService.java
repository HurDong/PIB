package com.practice.is.blood.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String userId, String message) {
		int partition = Math.abs(userId.hashCode()) % 3; // user-events 토픽에 대해 3개의 파티션 생성.
		kafkaTemplate.send("user-events", partition, userId, message);
	}
}
