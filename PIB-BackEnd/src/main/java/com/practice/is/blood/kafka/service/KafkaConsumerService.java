package com.practice.is.blood.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    @KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")), groupId = "user-consumer-group")
    public void listenToPartition0(@Payload String message, @Header("kafka_receivedMessageKey") String senderId) {
        System.out.println("송신자 ID: " + senderId + " | 0 파티션 메시지 수신: " + message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")), groupId = "user-consumer-group")
    public void listenToPartition1(@Payload String message, @Header("kafka_receivedMessageKey") String senderId) {
        System.out.println("송신자 ID: " + senderId + " | 1 파티션 메시지 수신: " + message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "user-events", partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "0")), groupId = "user-consumer-group")
    public void listenToPartition2(@Payload String message, @Header("kafka_receivedMessageKey") String senderId) {
        System.out.println("송신자 ID: " + senderId + " | 2 파티션 메시지 수신: " + message);
    }
}
