//package com.testtask.kafkaspring.intgr.consumer;
//
//import com.testtask.kafkaspring.intgr.AbstractTest;
//import com.testtask.kafkaspring.model.AvroUser;
//import com.testtask.kafkaspring.service.KafkaConsumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//
//public class KafkaConsumerTest extends AbstractTest {
//
//    @Autowired
//    private KafkaConsumer kafkaConsumer;
//
//    @Test
//    public void producerSendSuccess() {
//        AvroUser user = AvroUser.newBuilder()
//                                .setName("Alex")
//                                .setAge(50)
//                                .build();
//        producer.send(new ProducerRecord<String, AvroUser>("users", user.getName().toString(), user));
//
//        ConsumerRecord<String, AvroUser> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, "users");
//        assertNotNull(consumerRecord);
//        assertEquals("users", consumerRecord.topic());
//        assertEquals("Alex", consumerRecord.key());
//        assertNotNull(consumerRecord.value());
////        assertEquals("{\"name\": \"Alex\", \"age\": 50}", consumerRecord.value().toString());
//    }
//
//}
