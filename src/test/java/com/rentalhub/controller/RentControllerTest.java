package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.RentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createRentTest() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2021, 06, 01, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 07, 01, 10, 0);
        UUID uuid = UUID.fromString("5cedaf61-a799-45eb-a02f-5f90151ac66a");

        RentDto dto = new RentDto(uuid, "ABNASDG73FB", "jajko2", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createRentWithInvalidVinTest() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2021, 06, 01, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 07, 01, 10, 0);
        UUID uuid = UUID.fromString("5cedaf61-a799-45eb-a02f-5f90151ac66a");

        RentDto dto = new RentDto(uuid, "INVALID VIN", "jajko2", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRentWithInvalidLoginTest() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2021, 06, 01, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 07, 01, 10, 0);
        UUID uuid = UUID.fromString("5cedaf61-a799-45eb-a02f-5f90151ac66a");

        RentDto dto = new RentDto(uuid, "ABNASDG73FB", "INVALID LOGIN", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRentTest() throws Exception {
        mvc.perform(get("/api/rents/{uuid}", "296dcd4f-e1c9-496e-8944-a6f118bfeb45")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
