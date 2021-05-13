package com.rentalhub.currency;

import com.rentalhub.exception.CurrencyServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ExchangeRatesReceiver {

    @Value("${api.key}")
    private String apiKey;

    @Value("${currency.api.uri}")
    private String apiURI;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public double getExchangeRate(AcceptedCurrencies currency) throws CurrencyServiceException {
        return getExchangeRates().get(currency);
    }

    public Map<AcceptedCurrencies, Double> getExchangeRates() throws CurrencyServiceException {
        String uri = apiURI + "?access_key=" + apiKey + "&symbols=" +
                StringUtils.arrayToCommaDelimitedString(AcceptedCurrencies.values());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ExchangeRatesRespose> response = restTemplate.getForEntity(uri, ExchangeRatesRespose.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new CurrencyServiceException("Unspecified service error");
        }
        return response.getBody().getRates();
    }
}
