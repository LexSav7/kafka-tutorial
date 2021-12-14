package com.testtask.kafkaspring.intgr.producer;

import com.testtask.kafkaspring.intgr.AbstractTest;
import com.testtask.kafkaspring.model.AvroUser;
import com.testtask.kafkaspring.service.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KafkaProducerTest extends AbstractTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void should_send_event1() {
        AvroUser user = AvroUser.newBuilder()
                                .setName("Alex")
                                .setAge(50)
                                .build();
        kafkaProducer.produce(user);

        ConsumerRecord<String, AvroUser> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "users");
        assertNotNull(singleRecord);
        assertEquals("users", singleRecord.topic());
        assertEquals("Alex", singleRecord.key());
        assertNotNull(singleRecord.value());
    }
}