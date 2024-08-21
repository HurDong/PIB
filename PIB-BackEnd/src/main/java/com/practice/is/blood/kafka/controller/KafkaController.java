package com.practice.is.blood.kafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.kafka.service.KafkaProducerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class KafkaController {

	private final KafkaProducerService kafkaProducerService;

	@PostMapping("/send")
	public void sendMessage(@RequestParam("message") String message) {
		kafkaProducerService.sendMessage("test_topic", message);
	}
}