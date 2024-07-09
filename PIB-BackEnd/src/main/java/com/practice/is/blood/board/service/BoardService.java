package com.practice.is.blood.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.is.blood.board.domain.Board;
import com.practice.is.blood.board.domain.BoardMapper;
import com.practice.is.blood.board.domain.request.BoardRequest;
import com.practice.is.blood.board.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardMapper boardMapper;

	@Transactional
	public Board save(BoardRequest board) {
		Board newBoard = boardRepository.save(boardMapper.boardRequestToEntity(board));
		return newBoard;
	}

}
