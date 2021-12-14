package com.testtask.kafkaspring.unit.producer;

import com.testtask.kafkaspring.model.AvroUser;
import com.testtask.kafkaspring.service.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerUnitTest {

    @Mock
    KafkaTemplate<String,AvroUser> kafkaTemplate;

    @InjectMocks
    KafkaProducer kafkaProducer;

    @Test
    void sendProducerTestFailure()  {
        AvroUser user = AvroUser.newBuilder()
                .setName("Alex")
                .setAge(50)
                .build();
        SettableListenableFuture future = new SettableListenableFuture();

        future.setException(new RuntimeException("Exception Calling Kafka"));
        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);

        assertThrows(Exception.class, ()-> kafkaProducer.produce(user).get());
    }

//    @Test
//    void sendLibraryEvent_Approach2_success() throws ExecutionException, InterruptedException {
//        //given
//        AvroUser user = AvroUser.newBuilder()
//                .setName("Alex")
//                .setAge(50)
//                .build();
//        SettableListenableFuture future = new SettableListenableFuture();
//
//        ProducerRecord<String, AvroUser> producerRecord = new ProducerRecord("users", user.getName(), user);
//        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("users", 1),
//                1,1,342,System.currentTimeMillis(), 1, 2);
//        SendResult<String, AvroUser> sendResult = new SendResult<>(producerRecord, recordMetadata);
////        SendResult<String, AvroUser> sendResult = new SendResult<>(producerRecord);
//
//        future.set(sendResult);
//        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
//        //when
//
//        ListenableFuture<SendResult<String, AvroUser>> listenableFuture =  kafkaProducer.produce(user);
//
//        //then
//        SendResult<String, AvroUser> sendResult1 = listenableFuture.get();
//        assert sendResult1.getRecordMetadata().partition()==1;
//    }
}
