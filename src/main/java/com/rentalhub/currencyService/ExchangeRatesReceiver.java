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

@Component
public class ExchangeRatesReceiver {

    @Value("${api.key}")
    private String apiKey;

    @Value("${currency.api.uri}")
    private String apiURI;

    @Autowired
    private RestTemplate restTemplate;

    public double getExchangeRate(AcceptedCurrencies currency) throws CurrencyServiceException {
        try {
            return getResponse().getBody().getRates().get(currency);
        } catch (CurrencyClientErrorException | CurrencyServerErrorException | NullPointerException e) {
            throw new CurrencyServiceException("Unable to receive exchange rates", e);
        }
    }

    public ResponseEntity<ExchangeRatesResponse> getResponse() {
        String uri = apiURI + "?access_key=" + apiKey + "&symbols=" +
                StringUtils.arrayToCommaDelimitedString(AcceptedCurrencies.values());

        return restTemplate.getForEntity(uri, ExchangeRatesResponse.class);
    }
}
