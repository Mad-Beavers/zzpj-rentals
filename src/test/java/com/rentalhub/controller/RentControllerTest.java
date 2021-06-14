package com.rentalhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentalhub.dto.RentDto;
import com.rentalhub.model.*;
import com.rentalhub.repository.ArchivedRentRepository;
import com.rentalhub.repository.RentRepository;
import com.rentalhub.repository.VehicleRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class RentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private RentRepository rentRepository;

    @MockBean
    private ArchivedRentRepository archivedRentRepository;

    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = LocalDateTime.now().plusDays(7);

    Client testClient = new Client("testHash", "test", "test@test.com", "test",
            "test", "test", Set.of(DrivingLicenseCategory.B));

    Vehicle testVehicle = new Vehicle("testVin", "Ford", "Mustang", 5, 5.0,
            DrivingLicenseCategory.B);

    Rent testRent = new Rent(testVehicle, testClient, startDate, endDate);


    @Test
    void createValidRentTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenAnswer(i -> i.getArguments()[0]);

        RentDto dto = new RentDto(uuid, "testVin", "test", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(rentRepository, Mockito.times(1))
                .save(Mockito.any(Rent.class));
    }

    @Test
    void createRentForUnavailableVehicleTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Vehicle testVehicle = new Vehicle("testVin", "Ford", "Mustang", false,
                5, 5.0, DrivingLicenseCategory.B);

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenAnswer(i -> i.getArguments()[0]);

        RentDto dto = new RentDto(uuid, "testVin", "test", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        Mockito.verify(rentRepository, Mockito.times(0))
                .save(Mockito.any(Rent.class));
    }

    @Test
    void createRentForInsufficientDLCTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Client testClient = new Client("testHash", "test", "test@test.com", "test",
                "test", "test", Set.of(DrivingLicenseCategory.A));

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenAnswer(i -> i.getArguments()[0]);

        RentDto dto = new RentDto(uuid, "testVin", "test", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        Mockito.verify(rentRepository, Mockito.times(0))
                .save(Mockito.any(Rent.class));
    }

    @Test
    void createRentWithInvalidVinTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.empty());
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenAnswer(i -> i.getArguments()[0]);

        RentDto dto = new RentDto(uuid, "INVALID VIN", "test", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(rentRepository, Mockito.times(0))
                .save(Mockito.any(Rent.class));
    }

    @Test
    void createRentWithInvalidLoginTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.empty());
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.save(Mockito.any(Rent.class))).thenAnswer(i -> i.getArguments()[0]);

        RentDto dto = new RentDto(uuid, "testVin", "INVALID LOGIN", startDate, endDate, null);

        mvc.perform(post("/api/rents/createRent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(rentRepository, Mockito.times(0))
                .save(Mockito.any(Rent.class));
    }

    @Test
    void getRentTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(rentRepository.findByUuid(uuid)).thenReturn(Optional.of(testRent));

        mvc.perform(get("/api/rents/{uuid}", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getRentsTest() throws Exception {
        Mockito.when(rentRepository.findAll()).thenReturn(List.of(testRent));

        mvc.perform(get("/api/rents/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void endRentTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.findByUuid(uuid)).thenReturn(Optional.of(testRent));
        Mockito.when(archivedRentRepository.save(Mockito.any(ArchivedRent.class))).thenAnswer(i -> i.getArguments()[0]);

        mvc.perform(delete("/api/rents/close/{uuid}/{currencyAbbrev}", uuid.toString(), "EUR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(archivedRentRepository, Mockito.times(1))
                .save(Mockito.any(ArchivedRent.class));
    }

    @Test
    void endNonExistingRentTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.findByUuid(uuid)).thenReturn(Optional.empty());
        Mockito.when(archivedRentRepository.save(Mockito.any(ArchivedRent.class))).thenAnswer(i -> i.getArguments()[0]);

        mvc.perform(delete("/api/rents/close/{uuid}/{currencyAbbrev}", uuid.toString(), "EUR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(archivedRentRepository, Mockito.times(0))
                .save(Mockito.any(ArchivedRent.class));
    }

    @Test
    void endRentWithInvalidCurrencyTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        Mockito.when(clientRepository.findByLogin("test")).thenReturn(Optional.of(testClient));
        Mockito.when(vehicleRepository.findByVin("testVin")).thenReturn(Optional.of(testVehicle));
        Mockito.when(rentRepository.findByUuid(uuid)).thenReturn(Optional.of(testRent));
        Mockito.when(archivedRentRepository.save(Mockito.any(ArchivedRent.class))).thenAnswer(i -> i.getArguments()[0]);

        mvc.perform(delete("/api/rents/close/{uuid}/{currencyAbbrev}", uuid.toString(), "INVALID")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(archivedRentRepository, Mockito.times(0))
                .save(Mockito.any(ArchivedRent.class));
    }
}
