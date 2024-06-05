package com.cloud.crypto_prices.service;

import org.springframework.stereotype.Service;

import com.cloud.crypto_prices.model.CryptoPrice;
import com.cloud.crypto_prices.repository.CryptoPriceRepository;

import java.time.LocalDateTime;

@Service
public class CryptoPriceService {

    private CryptoPriceRepository repository;

    public void saveCryptoPrice(CryptoPrice cryptoPrice) {
        cryptoPrice.setTimestamp(LocalDateTime.now());
        repository.save(cryptoPrice);
    }
}
