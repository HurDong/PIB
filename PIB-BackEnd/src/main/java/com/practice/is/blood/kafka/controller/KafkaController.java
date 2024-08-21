package com.practice.is.blood.kafka.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.kafka.service.KafkaProducerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kafka")
public class KafkaController {

	private final KafkaProducerService kafkaProducerService;

	@PostMapping("/send/{userId}")
	public void sendMessageToKafka(@PathVariable (name= "userId") String userId, @RequestBody String message) {
		kafkaProducerService.sendMessage(userId, message);
	}
}