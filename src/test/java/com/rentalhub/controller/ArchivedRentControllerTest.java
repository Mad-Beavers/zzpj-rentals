package com.rentalhub.controller;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.model.*;
import com.rentalhub.repository.ArchivedRentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ArchivedRentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArchivedRentRepository archivedRentRepository;

    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = LocalDateTime.now().plusDays(7);

    Client testClient = new Client("testHash", "test", "test@test.com", "test",
            "test", "test", Set.of(DrivingLicenseCategory.B));

    Vehicle testVehicle = new Vehicle("testVin", "Ford", "Mustang", 5, 5.0,
            DrivingLicenseCategory.B);

    Rent testRent = new Rent(testVehicle, testClient, startDate, endDate);

    ArchivedRent archivedRent = new ArchivedRent(testRent.getUuid(), testRent, 10.0,
            AcceptedCurrencies.EUR.name(), 4.0);

    @Test
    void getArchivedRentTest() throws Exception {
        Mockito.when(archivedRentRepository.findByUuid(testRent.getUuid())).thenReturn(Optional.of(archivedRent));

        mvc.perform(get("/api/archived-rents/{uuid}", testRent.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getArchivedRentsTest() throws Exception {
        Mockito.when(archivedRentRepository.findByUuid(testRent.getUuid())).thenReturn(Optional.of(archivedRent));

        mvc.perform(get("/api/archived-rents/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
