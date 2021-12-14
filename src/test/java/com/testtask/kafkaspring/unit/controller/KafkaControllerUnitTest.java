package com.testtask.kafkaspring.unit.controller;

import com.testtask.kafkaspring.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KafkaController.class)
@AutoConfigureMockMvc
public class KafkaControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    KafkaProducer kafkaProducer;

    @Test
    void postUser() throws Exception {

        mockMvc.perform(post("/kafka/publish")
                        .param("name", "Test")
                        .param("age", "50"))
                .andExpect(status().isOk());

    }

    @Test
    void postLibraryEvent_4xx() throws Exception {

        mockMvc.perform(post("/kafka/publish")
                        .param("age", "50"))
                .andExpect(status().is4xxClientError());

    }
}