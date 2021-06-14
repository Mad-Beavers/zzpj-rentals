package com.rentalhub.currency;

import lombok.Getter;

import java.util.Map;

@Getter
public class ExchangeRatesRespose {

    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<AcceptedCurrencies, Double> rates;
}
