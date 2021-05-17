package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.DrivingLicenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void getClientTest() throws Exception {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        ClientRegistrationDto requestBody = new ClientRegistrationDto("alek", "lolek@gmail.com",
                "12345678", "Lolek", "Bolek", "+48123123123", dlc);
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        mvc.perform(get("/api/user/getClient")
                .contentType(MediaType.APPLICATION_JSON).content("alek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lolek"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").value("Bolek"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").isNotEmpty());


    }

    @Test
    void blockTest() throws Exception {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        ClientRegistrationDto requestBody = new ClientRegistrationDto("tolek", "lolek@gmail.com",
                "12345678", "Lolek", "Bolek", "+48123123123", dlc);
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        mvc.perform(put("/api/user/blockClient")
                .contentType(MediaType.APPLICATION_JSON).content("tolek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/getClient")
                .contentType(MediaType.APPLICATION_JSON).content("tolek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }

    @Test
    void unblockTest() throws Exception {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        ClientRegistrationDto requestBody = new ClientRegistrationDto("kalek", "lolek@gmail.com",
                "12345678", "Lolek", "Bolek", "+48123123123", dlc);
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        mvc.perform(put("/api/user/blockClient")
                .contentType(MediaType.APPLICATION_JSON).content("kalek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/user/getClient")
                .contentType(MediaType.APPLICATION_JSON).content("kalek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }


    @Test
    void editTest() throws Exception {
        Set<DrivingLicenseCategory> dlc = Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1);
        ClientRegistrationDto requestBody = new ClientRegistrationDto("tolek", "lolek@gmail.com",
                "12345678", "Lolek", "Bolek", "+48123123123", dlc);
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestBody))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


        mvc.perform(get("/api/user/getClient")
                .contentType(MediaType.APPLICATION_JSON).content("tolek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lolek"));


        ClientDto clientDto = new ClientDto("tolek", "lolek@gmail.com","Tolek", "Bolek", "+48123123123", dlc);
        mvc.perform(put("/api/user/editClient")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(clientDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mvc.perform(get("/api/user/getClient")
                .contentType(MediaType.APPLICATION_JSON).content("tolek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tolek"));
    }


    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
