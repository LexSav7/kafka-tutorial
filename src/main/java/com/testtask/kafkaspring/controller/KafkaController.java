package com.testtask.kafkaspring.controller;

import com.testtask.kafkaspring.model.AvroUser;
import com.testtask.kafkaspring.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer producer;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("name") String message, @RequestParam("age") String age) {
        AvroUser user = AvroUser.newBuilder()
                                .setName(message)
                                .setAge(Integer.parseInt(age))
                                .build();
        this.producer.produce(user);
    }
}