package com.practice.is.blood.openai.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.is.blood.config.OpenAiConfig;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OpenAiService {

	private final OpenAiConfig openAiConfig;

	private static final Logger logger = LoggerFactory.getLogger(OpenAiService.class);

	public List<Map<String, Object>> vieModelList() {
		logger.debug("[+] 모델 리스트를 조회합니다.");
		List<Map<String, Object>> resultList = null;

		// [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
		HttpHeaders headers = openAiConfig.httpHeaders();

		// [STEP2] 통신을 위한 RestTemplate을 구성합니다.
		ResponseEntity<String> response = openAiConfig.restTemplate().exchange("https://api.openai.com/v1/models",
				HttpMethod.GET, new HttpEntity<>(headers), String.class);

		try {
			// [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> responseData = om.readValue(response.getBody(),
					new TypeReference<Map<String, Object>>() {
					});
			resultList = (List<Map<String, Object>>) responseData.get("data");
		} catch (JsonMappingException e) {
			logger.debug("JsonMappingException :: " + e.getMessage());
		} catch (JsonProcessingException e) {
			logger.debug("RuntimeException :: " + e.getMessage());
		}
		return resultList;
	}

	public String submitPrompt(String userInput) {
		// TODO Auto-generated method stub
		return null;
	}
}
