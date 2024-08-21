package com.practice.is.blood.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
	@KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")), groupId = "user-consumer-group")
	public void listenToPartition0(String message) {
		System.out.println("0 파티션 메시지 수신 :  " + message);
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")), groupId = "user-consumer-group")
	public void listenToPartition1(String message) {
		System.out.println("1 파티션 메시지 수신 : " + message);
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "0")), groupId = "user-consumer-group")
	public void listenToPartition2(String message) {
		System.out.println("2 파티션 메시지 수신 : " + message);
	}
}
