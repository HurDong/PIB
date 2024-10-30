package com.practice.is.blood.elastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticConfig {
	@Bean
	public RestHighLevelClient restHighLevelClient(@Value("${spring.elasticsearch.host}") String elasticsearchHost) {
		return new RestHighLevelClient(
			RestClient.builder(new HttpHost(elasticsearchHost, 9200, "http")));
	}
}
