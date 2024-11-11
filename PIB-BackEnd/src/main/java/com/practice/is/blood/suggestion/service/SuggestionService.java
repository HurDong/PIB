package com.practice.is.blood.suggestion.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.practice.is.blood.suggestion.security.Signatures;

@Service
public class SuggestionService {
	private final String baseUrl = "https://api.searchad.naver.com";
	private final String path = "/keywordstool";
	private final String accessKey = ""; // 액세스키
	private final String secretKey = "";  // 시크릿키
	private final String customerId = "";  // ID

	public void getSuggestions(String keyword) {
		String parameter = "hintKeywords=";
		long timeStamp = System.currentTimeMillis();
		URL url;
		String times = String.valueOf(timeStamp);

		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");
			System.out.println("keyword : " + keyword);
		} catch (Exception e) {
			throw new RuntimeException("인코딩 실패.");
		}

		try {
			url = new URL(baseUrl + path + "?" + parameter + keyword);
			System.out.println("url : " + url);
			String signature = Signatures.of(times, "GET", path, secretKey);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("X-Timestamp", times);
			con.setRequestProperty("X-API-KEY", accessKey);
			con.setRequestProperty("X-Customer", customerId);
			con.setRequestProperty("X-Signature", signature);
			con.setDoOutput(true);

			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.println("responseCode : " + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println("Response : " + response.toString());

		} catch (Exception e) {
			System.out.println("Wrong URL.");
			throw new RuntimeException("네이버 API 호출 실패.");
		}
	}
}
