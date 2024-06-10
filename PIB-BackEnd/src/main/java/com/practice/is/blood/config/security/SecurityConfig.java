package com.practice.is.blood.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// h2 DB 사용을 위한 접근 권한 활성화
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // H2 콘솔 사용을 위해 CSRF 보호 비활성화
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/h2-console/**").permitAll().anyRequest()
						.authenticated())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

		return http.build();
	}
}
