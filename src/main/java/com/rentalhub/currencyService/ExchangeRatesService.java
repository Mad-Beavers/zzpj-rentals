package com.rentalhub.currencyService;

import com.rentalhub.exception.CurrencyClientErrorException;
import com.rentalhub.exception.CurrencyServerErrorException;
import com.rentalhub.exception.CurrencyServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class ExchangeRatesService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${currency.api.uri}")
    private String apiURI;

    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRatesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getExchangeRate(AcceptedCurrencies currency) throws CurrencyServiceException {
        if (currency == AcceptedCurrencies.PLN) {
            return 1.0;
        }

        try {
            Map<AcceptedCurrencies, Double> response = getResponse().getBody().getRates();
            double currencyToEUR = response.get(currency);
            double plnToEUR = response.get(AcceptedCurrencies.PLN);
            return round(plnToEUR / currencyToEUR, 2);
        } catch (CurrencyClientErrorException | CurrencyServerErrorException | NullPointerException e) {
            throw new CurrencyServiceException("Unable to receive exchange rates", e);
        }
    }

    public ResponseEntity<ExchangeRatesResponse> getResponse() {
        String uri = apiURI + "?access_key=" + apiKey + "&symbols=" +
                StringUtils.arrayToCommaDelimitedString(AcceptedCurrencies.values());

        return restTemplate.getForEntity(uri, ExchangeRatesResponse.class);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
