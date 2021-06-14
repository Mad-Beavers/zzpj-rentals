package com.rentalhub.currencyService;

import com.rentalhub.exception.CurrencyClientErrorException;
import com.rentalhub.exception.CurrencyServerErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
@DirtiesContext
public class ExchangeRatesServiceTest {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${currency.api.uri}")
    private String apiURI;

    private String uri;

    @BeforeEach
    public void setUp() {
        uri = apiURI + "?access_key=" + apiKey + "&symbols=" +
                StringUtils.arrayToCommaDelimitedString(AcceptedCurrencies.values());
    }

    @Test
    void exchangeRatesServiceTest() throws Exception {
        ExchangeRatesResponse testResponse = new ExchangeRatesResponse(true, 1l, "EUR",
                LocalDateTime.now().toString(), Map.of(
                AcceptedCurrencies.EUR, 1.0,
                AcceptedCurrencies.PLN, 4.23,
                AcceptedCurrencies.USD, 1.19
        ));
        Mockito.when(exchangeRatesService.getResponse()).thenReturn(ResponseEntity.ok(testResponse));
        System.out.println(exchangeRatesService.getExchangeRate(AcceptedCurrencies.USD));

        assertTrue(exchangeRatesService.getExchangeRate(AcceptedCurrencies.USD) == 3.55);
    }

    @Test
    void exchangeRatesPLNTest() throws Exception {
        ExchangeRatesResponse testResponse = new ExchangeRatesResponse(true, 1l, "EUR",
                LocalDateTime.now().toString(), Map.of(
                AcceptedCurrencies.EUR, 1.0,
                AcceptedCurrencies.PLN, 4.23,
                AcceptedCurrencies.USD, 1.19
        ));
        Mockito.when(exchangeRatesService.getResponse()).thenReturn(ResponseEntity.ok(testResponse));
        System.out.println(exchangeRatesService.getExchangeRate(AcceptedCurrencies.PLN));

        assertTrue(exchangeRatesService.getExchangeRate(AcceptedCurrencies.PLN) == 1.0);
    }

    @Test
    void testClientError() {
        Mockito.when(restTemplate.getForEntity(uri, ExchangeRatesResponse.class))
                .thenThrow(new CurrencyClientErrorException("test error message"));
        assertThrows(CurrencyClientErrorException.class, () -> exchangeRatesService.getResponse());
    }

    @Test
    void testServerError() {
        Mockito.when(restTemplate.getForEntity(uri, ExchangeRatesResponse.class))
                .thenThrow(new CurrencyServerErrorException("test error message"));
        assertThrows(CurrencyServerErrorException.class, () -> exchangeRatesService.getResponse());
    }
}
