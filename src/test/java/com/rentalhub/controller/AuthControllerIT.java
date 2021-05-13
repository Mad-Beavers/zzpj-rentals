package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.RentalHubApplication;
import com.rentalhub.dto.AuthRequestDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.DrivingLicenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {RentalHubApplication.class})
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integration.properties")
class AuthControllerIT {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void registerClient() throws Exception {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        ClientRegistrationDto requestBody = new ClientRegistrationDto("lolek", "lolek@gmail.com",
                "12345678", "Lolek", "Bolek", "+48123123123", dlc);
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void login() throws Exception {
        AuthRequestDto authRequestDto = new AuthRequestDto("lolek", "12345678");
        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(authRequestDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}