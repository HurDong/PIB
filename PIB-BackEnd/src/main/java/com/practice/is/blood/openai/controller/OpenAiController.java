package com.practice.is.blood.openai.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class OpenAiController {
	private static final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

	private final OpenAiService openAiService;

	@Value("${open-ai-key}")
	String openAiKey;

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		System.out.println(openAiKey);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/prompt")
	public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
		String userInput = request.get("input");
		logger.info("유저 입력 : {}", userInput);
		String response = openAiService.submitPrompt(userInput);
		return ResponseEntity.ok(response);
	}
}
