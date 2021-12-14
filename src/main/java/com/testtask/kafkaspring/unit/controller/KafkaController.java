package com.testtask.kafkaspring.unit.controller;


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

    private final KafkaProducer producerService;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam String name, @RequestParam int age) {
        AvroUser user = AvroUser.newBuilder()
                                .setName(name)
                                .setAge(age)
                                .build();
        this.producerService.produce(user);
    }

//    @PostMapping(value = "/v2/publish")
//    public void produce(@RequestParam ) {
//        new AvroUser();
//        this.producerService.produceByRest(user);
//    }
}