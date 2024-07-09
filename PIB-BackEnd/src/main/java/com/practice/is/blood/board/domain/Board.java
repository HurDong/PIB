package com.practice.is.blood.board.domain;

import java.util.UUID;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id = UlidCreator.getMonotonicUlid().toUuid();
	private int memberId;
	private String title;
	private String content;
	private String type;
	@Column(columnDefinition = "int default 0")
	private int views;
	@Column(columnDefinition = "int default 0")
	private int likes;

	public Board(int memberId, String title, String content, String type) {
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.type = type;
	}
}
