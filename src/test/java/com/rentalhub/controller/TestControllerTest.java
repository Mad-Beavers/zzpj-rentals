package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.AuthRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.rentalhub.security.jwt.JwtFilter.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {RentalHubApplication.class})
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integration.properties")
class TestControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void testAdminSecuredEndpoint() throws Exception {
        String adminToken = getAdminToken();
        mvc.perform(get("/api/test/admin_test")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isAccepted()).andReturn();
    }

    @Test
    void testClientSecuredEndpoint() throws Exception {
        String clientToken = getClientToken();
        mvc.perform(get("/api/test/client_test")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer " + clientToken)
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isAccepted()).andReturn();
    }

    String getClientToken() throws Exception {
        AuthRequestDto authRequestDto = new AuthRequestDto("lolek", "12345678");
        MvcResult result = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(authRequestDto))
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isAccepted()).andReturn();
        return result.getResponse().getContentAsString();
    }

    String getAdminToken() throws Exception {
        AuthRequestDto authRequestDto = new AuthRequestDto("tola", "12345678");
        MvcResult result = mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(authRequestDto))
                .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isAccepted()).andReturn();
        return result.getResponse().getContentAsString();
    }

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}