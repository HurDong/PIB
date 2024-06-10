package com.practice.is.blood.openai.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserPrompt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer promptId;
	private String content;
}
