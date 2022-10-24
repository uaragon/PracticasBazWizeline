package com.baz.wizeline.javamaven.configuration;
import org.junit.Assert;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

class KafkaConfigurationTest {
    public static final Logger logger = LoggerFactory.getLogger(KafkaConfigurationTest.class);
    @Test
    void producerFactory() {
//Valida Si el el producer viene vacío

        Map<String, Object> config = new HashMap<>();
        logger.info("Se cargan los valores del productor");
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        logger.info("Se valida si el Mapeo de productor viene nulo");
        Assert.assertNotNull("",config);

    }
    @Test
    void consumerFactory() {
        //Valida Si el el consumer viene vacío
        Map<String, Object> config = new HashMap<>();
        logger.info("Se cargan los valores del consumidor ");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        logger.info("Valida si el objeto no viene nulo ");
        Assert.assertNotNull("Exitoso",config);
        logger.info("Validacion exitosa ");

    }

}