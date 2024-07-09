package com.practice.is.blood.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.board.domain.Board;
import com.practice.is.blood.board.domain.request.BoardRequest;
import com.practice.is.blood.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;

	@PostMapping("/")
	public ResponseEntity<Board> createBoard(@RequestBody BoardRequest board) {
		return ResponseEntity.ok(boardService.save(board));
	}

}
