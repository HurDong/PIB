package com.practice.is.blood.sse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Random;

@RestController
public class SseController {

	@GetMapping("/sse")
	public SseEmitter streamSse() {
		SseEmitter emitter = new SseEmitter(); // 기본 타임아웃 시간 (30초)

		// 별도의 스레드에서 데이터를 전송
		new Thread(() -> {
			try {
				Random random = new Random();
				for (int i = 0; i < 5; i++) {
					String randomMessage = "Random Message " + random.nextInt(100); // 0~99 사이의 랜덤 메시지 생성
					emitter.send(randomMessage); // 메시지 전송
					Thread.sleep(3000); // 3초 대기
				}
				emitter.complete(); // 5번 전송 후 연결 종료
			} catch (IOException | InterruptedException e) {
				emitter.completeWithError(e); // 에러 발생 시 연결 종료
			}
		}).start();

		return emitter;
	}
}
