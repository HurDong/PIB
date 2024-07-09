package com.practice.is.blood.board.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.is.blood.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

}
