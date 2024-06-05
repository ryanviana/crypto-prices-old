package com.cloud;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CryptoProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoProducer.class);
    private static final String CRYPTOCOMPARE_API_URL = "https://min-api.cryptocompare.com/data/v2/histominute?fsym=BTC&tsym=USD&limit=1";
    private static final String TOPIC = "crypto_currencies";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CryptoProducer(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 60000) // Fetch every 60 seconds
    public void sendBitcoinUpdate() {
        try {
            String response = restTemplate.getForObject(CRYPTOCOMPARE_API_URL, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.path("Data").path("Data").get(0); // Get the first data point
            String message = objectMapper.writeValueAsString(dataNode);
            LOGGER.info("Sending message to topic {}: {}", TOPIC, message);
            kafkaTemplate.send(TOPIC, message);
        } catch (IOException e) {
            LOGGER.error("Error fetching or parsing data from CryptoCompare API", e);
        }
    }
}
