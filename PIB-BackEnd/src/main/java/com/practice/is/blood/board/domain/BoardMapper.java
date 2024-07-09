package com.practice.is.blood.board.domain;

import org.springframework.stereotype.Component;

import com.practice.is.blood.board.domain.request.BoardRequest;

@Component
public class BoardMapper {
	public Board boardRequestToEntity(BoardRequest request) {
		return new Board(request.getMemberId(),request.getTitle(),request.getContent(),request.getType());
	}
}
