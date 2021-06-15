package com.rentalhub.service;

import com.rentalhub.exception.NoSuchClientException;
import com.rentalhub.exception.NoSuchRentException;
import com.rentalhub.exception.UnavailableVehicleException;
import com.rentalhub.model.*;
import com.rentalhub.repository.RentRepository;
import com.rentalhub.repository.VehicleRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RentServiceTest {

    @Autowired
    private RentService rentService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RentRepository rentRepository;

    @Test
    void acceptRent() throws NoSuchRentException, UnavailableVehicleException, NoSuchClientException {
        Client testClient = new Client("this is passsword", "aradiuk" + randomAlphanumeric(8), randomAlphanumeric(8) + "aradiuk@test.com", "a",
                "r", "123456789", Map.of(DrivingLicenseCategory.B, LocalDateTime.of(2020, 4, 4, 4, 4)));

        Client testClient2 = new Client("this is passsword", "aradiuk" + randomAlphanumeric(8), randomAlphanumeric(8) + "aradiuk@test.com", "a",
                "r", "123456789", Map.of(DrivingLicenseCategory.B, LocalDateTime.of(2019, 4, 4, 4, 4)));

        Client testClient3 = new Client("this is passsword", "aradiuk" + randomAlphanumeric(8), randomAlphanumeric(8) + "aradiuk@test.com", "a",
                "r", "123456789", Map.of(DrivingLicenseCategory.B, LocalDateTime.of(2020, 9, 5, 2, 4)));

        Client testClient4 = new Client("this is passsword", "aradiuk" + randomAlphanumeric(8), randomAlphanumeric(8) + "aradiuk@test.com", "a",
                "r", "123456789", Map.of(DrivingLicenseCategory.B, LocalDateTime.of(2017, 10, 3, 2, 4)));

        Client testClient5 = new Client("this is passsword", "aradiuk" + randomAlphanumeric(8), randomAlphanumeric(8) + "aradiuk@test.com", "a",
                "r", "123456789", Map.of(DrivingLicenseCategory.B, LocalDateTime.of(2021, 6, 7, 4, 4)));

        clientRepository.saveAll(List.of(testClient, testClient2, testClient3, testClient4, testClient5));


        Vehicle testVehicle = new Vehicle(randomAlphanumeric(12), "Ford", "Mustang", 5, 5.0, 123.0, VehicleType.sport,
                DrivingLicenseCategory.B);

        vehicleRepository.saveAndFlush(testVehicle);

        Rent testRent = new Rent(testVehicle, testClient, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        Rent testRent2 = new Rent(testVehicle, testClient2, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        Rent testRent3 = new Rent(testVehicle, testClient3, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        Rent testRent4 = new Rent(testVehicle, testClient4, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        rentRepository.saveAll(List.of(testRent, testRent2, testRent3, testRent4));


        Rent acceptedRent = rentService.acceptRent(LocalDateTime.now(), testVehicle.getVin());
        assertEquals(acceptedRent.getUuid(), testRent4.getUuid());
    }
}