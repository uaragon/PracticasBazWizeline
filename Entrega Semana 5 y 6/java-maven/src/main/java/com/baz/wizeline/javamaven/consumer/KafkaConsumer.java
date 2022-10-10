package com.baz.wizeline.javamaven.consumer;

import com.baz.wizeline.javamaven.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<User>> cr, @Payload User account) {
        System.out.println("Received: " + account.getUser());
    }
}