package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.AuthRequestDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.DrivingLicenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerIT {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private final String login = randomAlphanumeric(12);
    private final String password = randomAlphanumeric(20);
    private final String email = randomAlphabetic(8) + "@gmail.com";

    @Test
    void registerClient() throws Exception {
        ClientRegistrationDto requestBody = getClientRegistrationDto();

        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void login() throws Exception {
        ClientRegistrationDto requestBody = getClientRegistrationDto();

        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestBody)));

        AuthRequestDto authRequestDto = new AuthRequestDto(login, password);
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(authRequestDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    private ClientRegistrationDto getClientRegistrationDto() {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        return new ClientRegistrationDto(login, email,
                password, "Lolek", "Bolek", "+48123123123", dlc);
    }
}