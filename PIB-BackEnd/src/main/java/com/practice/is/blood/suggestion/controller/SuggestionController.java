package com.practice.is.blood.suggestion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.suggestion.service.SuggestionService;

@RestController
@RequestMapping("/api/v1/suggestion")
public class SuggestionController {
	private final SuggestionService suggestionService;

	public SuggestionController(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	@GetMapping("/api/keywords/synonyms")
	public ResponseEntity<List<String>> getKeywordSynonyms(@RequestParam String keyword) {
		suggestionService.getSuggestions(keyword);
		return ResponseEntity.ok().build();
	}
}
