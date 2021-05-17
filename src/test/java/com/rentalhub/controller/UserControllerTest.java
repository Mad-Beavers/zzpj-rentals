package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.AuthRequestDto;
import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.Set;

import static com.rentalhub.security.jwt.JwtFilter.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mvc.perform(get("/api/user/getUser")
                .contentType(MediaType.APPLICATION_JSON).content("alek").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.secondName").isNotEmpty());


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
