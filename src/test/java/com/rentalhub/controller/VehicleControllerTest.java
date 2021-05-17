package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.dto.VehicleDto;
import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VehicleControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void createTest() throws Exception {
        VehicleDto vehicleDto = new VehicleDto("3HGCM82633A004352", "Ford", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/createVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void getVehicleTest() throws Exception {
        VehicleDto vehicleDto = new VehicleDto("4HGCM82633A004352", "Ford", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/createVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/vehicle/getVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("4HGCM82633A004352").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Ford"));
    }

    @Test
    void blockTest() throws Exception {
        VehicleDto vehicleDto = new VehicleDto("5HGCM82633A004352", "Ford", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/createVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/api/vehicle/blockVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("5HGCM82633A004352")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/vehicle/getVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("5HGCM82633A004352").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(false));
    }

    @Test
    void unblockTest() throws Exception {
        VehicleDto vehicleDto = new VehicleDto("6HGCM82633A004352", "Ford", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/createVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(put("/api/vehicle/unblockVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("6HGCM82633A004352")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/api/vehicle/getVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("6HGCM82633A004352").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true));
    }


    @Test
    void editTest() throws Exception {
        VehicleDto vehicleDto = new VehicleDto("7HGCM82633A004352", "Ford", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/createVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mvc.perform(get("/api/vehicle/getVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("7HGCM82633A004352").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Ford"));


        VehicleDto vehicleEditDto = new VehicleDto("7HGCM82633A004352", "Audi", "Mondeo",true,
                4, 2.0, DrivingLicenseCategory.B);
        mvc.perform(put("/api/vehicle/editVehicle")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(vehicleEditDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        mvc.perform(get("/api/vehicle/getVehicle")
                .contentType(MediaType.APPLICATION_JSON).content("7HGCM82633A004352").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Audi"));

    }

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
