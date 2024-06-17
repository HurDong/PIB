package com.practice.is.blood.openai.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.is.blood.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class OpenAiController {
	private static final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

	private final OpenAiService openAiService;

	@PostMapping("/prompt")
	public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
		String userInput = request.get("input");
		logger.info("유저 입력 : {}", userInput);
		String response = openAiService.submitPrompt(userInput);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/modelList")
	public ResponseEntity<List<Map<String, Object>>> selectModelList() {
		List<Map<String, Object>> response = openAiService.vieModelList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/crawl")
	public List<String> crawl() {
		List<String> companyNames = new ArrayList<>();
		String url = "https://jasoseol.com/recruit"; // 크롤링할 웹 페이지 URL

		try {
			// ChromeDriver 경로 설정
			String chromeDriverPath = new ClassPathResource("chromedriver.exe").getFile().getAbsolutePath();
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);

			WebDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			try {
				// 웹 페이지 로드
				driver.get(url);

				// 페이지 소스를 가져와 Jsoup으로 파싱
				String pageSource = driver.getPageSource();
				Document doc = Jsoup.parse(pageSource);

				// 회사명 추출
				Elements companyElements = doc.select("a.company div.company-name span");
				for (Element companyElement : companyElements) {
					companyNames.add(companyElement.text());
				}

			} finally {
				// WebDriver 종료
				driver.quit();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return companyNames;
	}
}
