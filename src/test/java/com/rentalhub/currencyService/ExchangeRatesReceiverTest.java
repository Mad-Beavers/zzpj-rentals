package com.rentalhub.currencyService;

import com.rentalhub.exception.CurrencyClientErrorException;
import com.rentalhub.exception.CurrencyServerErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
@DirtiesContext
public class ExchangeRatesReceiverTest {

    @Autowired
    private ExchangeRatesReceiver exchangeRatesReceiver;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${currency.api.uri}")
    private String apiURI;

    private MockRestServiceServer mockRestServiceServer;
    private String uri;

    @BeforeEach
    public void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        uri = apiURI + "?access_key=" + apiKey + "&symbols=" +
                StringUtils.arrayToCommaDelimitedString(AcceptedCurrencies.values());
    }

    @Test
    void testClientError() {
        assertThrows(CurrencyClientErrorException.class, () -> {
            mockRestServiceServer
                    .expect(requestTo(uri))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withBadRequest());

            exchangeRatesReceiver.getResponse();
        });
    }

    @Test
    void testServerError() {
        assertThrows(CurrencyServerErrorException.class, () -> {
            mockRestServiceServer
                    .expect(requestTo(uri))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withServerError());

            exchangeRatesReceiver.getResponse();
        });
    }
}
