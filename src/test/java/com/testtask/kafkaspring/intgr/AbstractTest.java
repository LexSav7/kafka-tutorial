package com.testtask.kafkaspring.intgr;

import com.testtask.kafkaspring.KafkaSpringApplication;
import com.testtask.kafkaspring.customconverter.CustomKafkaAvroDeserializer;
import com.testtask.kafkaspring.model.AvroUser;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {KafkaSpringApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@EmbeddedKafka()
public abstract class AbstractTest {

    @Autowired
    private KafkaProperties kafkaProperties;
    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    protected Producer<String, AvroUser> producer;
    protected Consumer<String, AvroUser> consumer;

    @Before
    public void setUp() {
        Map<String, Object> senderProps = kafkaProperties.buildProducerProperties();

        producer = new KafkaProducer<>(senderProps);

        //consumers used in test code needs to be created like this in code because otherwise it won't work
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("in-test-consumer", "false", kafkaEmbedded));
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomKafkaAvroDeserializer.class);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configs.put("schema.registry.url", "not-used");

        consumer = new DefaultKafkaConsumerFactory<String, AvroUser>(configs).createConsumer("in-test-consumer", "10");

        kafkaProperties.buildConsumerProperties();
        consumer.subscribe(Lists.newArrayList("users"));

    }

    @After
    public void reset() {
        consumer.close();
    }

}