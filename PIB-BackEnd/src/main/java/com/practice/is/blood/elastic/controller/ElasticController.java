package com.practice.is.blood.elastic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.elastic.service.ElasticService;

@RestController
@RequestMapping("/api/v1/elastic")
public class ElasticController {
	private final JdbcTemplate jdbcTemplate;
	private final ElasticService elasticService;

	public ElasticController(JdbcTemplate jdbcTemplate, ElasticService elasticService) {
		this.jdbcTemplate = jdbcTemplate;
		this.elasticService = elasticService;
	}

	@PostMapping("/execute")
	public String executeMigration() {
		try {
			// Execute SQL query using JdbcTemplate
			String sql = "SELECT * FROM board";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

			// Process results and insert into Elasticsearch
			for (Map<String, Object> row : rows) {
				elasticService.indexData(row);
			}
			return "Data migration completed successfully.";
		} catch (Exception e) {
			e.printStackTrace();
			return "Data migration failed.";
		}
	}
}
