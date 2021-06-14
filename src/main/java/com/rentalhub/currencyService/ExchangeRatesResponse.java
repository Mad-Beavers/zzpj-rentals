package com.rentalhub.currencyService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRatesResponse {

    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<AcceptedCurrencies, Double> rates;
}
