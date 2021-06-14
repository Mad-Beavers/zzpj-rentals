package com.rentalhub.costCalculator;

import com.rentalhub.service.CostCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class CostCalculatorTest {

    @Test
    public void basicCalculatorTest() {
        LocalDateTime start = LocalDateTime.of(2021, 6, 10, 15, 0, 0);
        LocalDateTime declaredEnd = LocalDateTime.of(2021, 6, 16, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 6, 18, 15, 0, 0);
        assertEquals(CostCalculator.calculateCost(start, declaredEnd, end, 10.0), 100.0);
    }

    @Test
    public void calculatorTestPartialDays() {
        LocalDateTime start = LocalDateTime.of(2021, 6, 10, 15, 0, 0);
        LocalDateTime declaredEnd = LocalDateTime.of(2021, 6, 16, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 6, 18, 13, 0, 0);
        assertEquals(CostCalculator.calculateCost(start, declaredEnd, end, 10.0), 100.0);
    }

    @Test
    public void calculatorTestPartialDays2() {
        LocalDateTime start = LocalDateTime.of(2021, 6, 10, 15, 0, 0);
        LocalDateTime declaredEnd = LocalDateTime.of(2021, 6, 16, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 6, 18, 18, 0, 0);
        assertEquals(CostCalculator.calculateCost(start, declaredEnd, end, 10.0), 100.0);
    }
}
