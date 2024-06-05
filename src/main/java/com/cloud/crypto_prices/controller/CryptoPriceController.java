package com.cloud.crypto_prices.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.crypto_prices.model.CryptoPrice;
import com.cloud.crypto_prices.repository.CryptoPriceRepository;

@RestController
@RequestMapping("/api")
public class CryptoPriceController {

    private CryptoPriceRepository cryptoPriceRepository;

    @GetMapping("/crypto-prices")
    public List<CryptoPrice> getCryptoPrices() {
        return cryptoPriceRepository.findAll();
    }
}
