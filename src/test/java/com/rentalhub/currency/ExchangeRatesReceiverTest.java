package com.rentalhub.currency;

import com.rentalhub.exception.CurrencyServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.EnumSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yaml")
public class ExchangeRatesReceiverTest {

    @Autowired
    ExchangeRatesReceiver exchangeRatesReceiver;

    @Test
    void testExchangeRatesAcquiring() throws CurrencyServiceException {
        Map<AcceptedCurrencies, Double> exchangeRates = exchangeRatesReceiver.getExchangeRates();

        for (AcceptedCurrencies currency : EnumSet.allOf(AcceptedCurrencies.class)) {
            assertNotNull(exchangeRates.get(currency));
        }
    }
}
