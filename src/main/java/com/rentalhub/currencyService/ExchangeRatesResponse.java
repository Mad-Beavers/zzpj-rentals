package com.rentalhub.currencyService;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExchangeRatesResponse {

    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<AcceptedCurrencies, Double> rates;
}
