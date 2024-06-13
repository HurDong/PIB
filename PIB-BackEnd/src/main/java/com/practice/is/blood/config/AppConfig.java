package com.practice.is.blood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	// openAi api를 rest하게 호출하기 위한 restTemplate 생성
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
