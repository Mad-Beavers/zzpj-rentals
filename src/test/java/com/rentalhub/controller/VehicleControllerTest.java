package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.VehicleDto;
import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.model.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetVehicleTest() throws Exception {
        String vin = randomAlphanumeric(12);
        VehicleDto vehicle = new VehicleDto(vin, "Mazda", "RX-7", 5, 2.4, true, VehicleType.sport, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/create-vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated());

        MockHttpServletResponse response = mvc.perform(get("/api/vehicle/" + vin)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        VehicleDto readVehicle = mapper.readValue(response.getContentAsString(), VehicleDto.class);
        assertEquals(readVehicle, vehicle);
    }

    @Test
    void editVehicleTest() throws Exception {
        VehicleDto vehicle = postSampleVehicle();
        VehicleDto originalVehicle = getVehicleByVin(vehicle.vin());

        String newBrand = "newBrand";
        VehicleDto updatedVehicle = new VehicleDto(vehicle.vin(), newBrand, vehicle.model(), vehicle.numberOfSeats(), vehicle.engineCapacity(), true, VehicleType.sport, vehicle.dlc());

        mvc.perform(put("/api/vehicle/update-vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedVehicle)))
                .andExpect(status().isOk());

        vehicle = getVehicleByVin(originalVehicle.vin());
        assertEquals(vehicle.vin(), originalVehicle.vin());
        assertNotEquals(originalVehicle.brand(), newBrand);
        assertEquals(vehicle.brand(), newBrand);
    }

    @Test
    void changeAvailabilityTest() throws Exception {
        VehicleDto vehicle = postSampleVehicle();
        assertTrue(getVehicleByVin(vehicle.vin()).available());

        mvc.perform(put("/api/vehicle/change-state/" + vehicle.vin() + "/false"))
                .andExpect(status().isOk());

        VehicleDto updatedVehicle = getVehicleByVin(vehicle.vin());
        assertFalse(updatedVehicle.available());
    }

    private VehicleDto postSampleVehicle() throws Exception {
        VehicleDto vehicle = new VehicleDto(randomAlphanumeric(12), "Mazda", "RX-7", 5, 2.4, true, VehicleType.sport, DrivingLicenseCategory.B);
        mvc.perform(post("/api/vehicle/create-vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vehicle)));
        return vehicle;
    }

    private VehicleDto getVehicleByVin(String vin) throws Exception {
        String responseString = mvc.perform(get("/api/vehicle/" + vin)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return mapper.readValue(responseString, VehicleDto.class);
    }
}