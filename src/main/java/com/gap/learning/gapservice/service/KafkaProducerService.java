package com.gap.learning.gapservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gap.learning.gapservice.dto.CartListDTO;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.cart.topic}")
    private String cartTopic;

    public CompletableFuture<SendResult<String, String>> sendMessageAsync(CartListDTO message, String userId) throws JsonProcessingException {

        String value  = objectMapper.writeValueAsString(message);

        Header headers = new RecordHeader("cart-topic", "cart-topic".getBytes());
        List<Header> headerList = List.of(headers);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(cartTopic, null, userId, value, headerList);

        return kafkaTemplate.send(producerRecord);

    }
}
