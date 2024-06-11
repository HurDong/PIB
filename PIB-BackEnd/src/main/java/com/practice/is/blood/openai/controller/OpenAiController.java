package com.practice.is.blood.openai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.openai.domain.UserPrompt;
import com.practice.is.blood.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class OpenAiController {

	private final OpenAiService openAiService;

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/prompt")
    public ResponseEntity<String> submitPrompt(@RequestBody UserPrompt userPrompt) {
        try {
            String response = openAiService.getChatGptResponse(userPrompt.getContent());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
