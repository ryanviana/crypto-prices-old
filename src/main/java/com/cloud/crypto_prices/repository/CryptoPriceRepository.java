package com.cloud.crypto_prices.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cloud.crypto_prices.model.CryptoPrice;

public interface CryptoPriceRepository extends MongoRepository<CryptoPrice, String> {
}
