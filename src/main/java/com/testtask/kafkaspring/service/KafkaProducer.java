package com.testtask.kafkaspring.service;

import com.testtask.kafkaspring.model.AvroUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    @Value("${topic.name}")
    private String TOPIC;

    private final KafkaTemplate<String, AvroUser> kafkaTemplate;

    public ListenableFuture<SendResult<String, AvroUser>> produce(AvroUser user) {

        log.info(String.format("#### -> Producing user from REST -> %s", user.toString()));

        ListenableFuture<SendResult<String, AvroUser>> future = this.kafkaTemplate.send(TOPIC, user.getName().toString(), user);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, AvroUser> result) {
                log.info("Sent message=[ {} ] with offset=[ {} ]", user, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[ {} ] due to : {}", user, ex.getMessage());
            }
        });
        return future;
    }

//    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
//    public void produceBySchedule() {
//
//        JsonUser user = new JsonUser.UserBuilder("Alex", "Savenko").age(25).build();
//        log.info(String.format("#### -> Producing message -> %s", user.toString()));
//
//        ListenableFuture<SendResult<String, JsonUser>> future = this.kafkaTemplate.send(TOPIC, user);
//
//        future.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onSuccess(SendResult<String, JsonUser> result) {
//                log.info("Sent message=[ {} ] with offset=[ {} ]", user, result.getRecordMetadata().offset());
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                log.info("Unable to send message=[ {} ] due to : {}", user, ex.getMessage());
//            }
//        });
//    }
}