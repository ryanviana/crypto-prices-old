package com.cloud.crypto_prices.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "cryptoPrices")
public class CryptoPrice {
    @Id
    private String id;
    private String currency;
    private double price;
    private LocalDateTime timestamp;
}
