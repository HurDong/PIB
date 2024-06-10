package com.practice.is.blood.openai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai")
public class OpenAiController {
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
