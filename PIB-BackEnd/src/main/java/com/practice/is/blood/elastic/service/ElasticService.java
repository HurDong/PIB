package com.practice.is.blood.elastic.service;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {
	private final RestHighLevelClient client;

	public ElasticService(RestHighLevelClient client) {
		this.client = client;
	}

	public void indexData(Map<String, Object> row) {
		try {
			String id = row.get("id").toString();
			int memberId = (int) row.get("memberId");
			String title = (String) row.get("title");
			String content = (String) row.get("content");
			String type = (String) row.get("type");
			int views = (int) row.get("views");
			int likes = (int) row.get("likes");

			// Create JSON document to insert into Elasticsearch
			String json = String.format(
				"{\"id\": \"%s\", \"memberId\": %d, \"title\": \"%s\", \"content\": \"%s\", \"type\": \"%s\", \"views\": %d, \"likes\": %d}",
				id, memberId, title, content, type, views, likes);

			IndexRequest request = new IndexRequest("board_index")
				.id(id)
				.source(json, XContentType.JSON);

			// Insert document into Elasticsearch
			client.index(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
