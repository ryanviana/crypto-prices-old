package com.cloud.crypto_prices.kafka;

import com.cloud.crypto_prices.model.CryptoPrice;
import com.cloud.crypto_prices.service.CryptoPriceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CryptoPriceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoPriceConsumer.class);

    private final CryptoPriceService cryptoPriceService;
    private final ObjectMapper objectMapper;

    public CryptoPriceConsumer(CryptoPriceService cryptoPriceService, ObjectMapper objectMapper) {
        this.cryptoPriceService = cryptoPriceService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "crypto_currencies", groupId = "crypto_group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            JsonNode dataNode = objectMapper.readTree(message);

            double price = dataNode.path("close").asDouble();
            CryptoPrice cryptoPrice = new CryptoPrice();
            cryptoPrice.setCurrency("BTC");
            cryptoPrice.setPrice(price);
            cryptoPrice.setTimestamp(LocalDateTime.now());

            // Save to MongoDB
            cryptoPriceService.saveCryptoPrice(cryptoPrice);

            LOGGER.info("Consumed and saved message: {}", cryptoPrice);
        } catch (IOException e) {
            LOGGER.error("Error parsing data from Kafka message", e);
        }
    }
}
