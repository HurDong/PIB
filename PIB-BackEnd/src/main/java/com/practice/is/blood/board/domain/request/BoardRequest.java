package com.practice.is.blood.board.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequest {
	private int memberId;
	private String title;
	private String content;
	private String type;
}
