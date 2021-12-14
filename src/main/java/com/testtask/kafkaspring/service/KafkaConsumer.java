package com.testtask.kafkaspring.service;

import com.testtask.kafkaspring.model.AvroUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@Getter
@NoArgsConstructor
public class KafkaConsumer {

    private static final String TOPIC = "users";
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = {TOPIC})
    public void consume(ConsumerRecord<String, AvroUser> user) {
        log.info("=> consumed {}", user.value());
        latch.countDown();
    }
}