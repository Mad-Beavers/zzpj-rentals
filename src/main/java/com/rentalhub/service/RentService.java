package com.rentalhub.service;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.dto.RentDto;
import com.rentalhub.exception.*;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RentService {

    private final RentRepository rentRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final ArchivedRentService archivedRentService;

    @Autowired
    public RentService(RentRepository rentRepository, VehicleService vehicleService, UserService userService, ArchivedRentService archivedRentService) {
        this.rentRepository = rentRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.archivedRentService = archivedRentService;
    }

    public Rent addRent(RentDto dto) throws UnavailableVehicleException, InsufficientClientDlcException,
            NoSuchClientException, NoSuchVehicleException {

        Optional<Vehicle> vehicle = vehicleService.getVehicle(dto.vin());
        Optional<Client> client = userService.getClient(dto.login());
        if (client.isEmpty()) {
            throw new NoSuchClientException("There is no client like that in the database");
        }
        if (vehicle.isEmpty()) {
            throw new NoSuchVehicleException("There is no vehicle like that in the database");
        }
        if (!vehicle.get().getAvailable()) {
            throw new UnavailableVehicleException("This vehicle is already rented");
        }
        if (!client.get().getDrivingLicenseCategories().contains(vehicle.get().getDlc())) {
            throw new InsufficientClientDlcException("Client does not have required driving license category");
        }
        vehicleService.changeAvailability(dto.vin(), false);
        Rent rent = new Rent(vehicle.get(), client.get(), dto.startDate(), dto.declaredFinishedDate(), dto.actualFinishedDate());

        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(UUID uuid) {
        return rentRepository.findByUuid(uuid);
    }

    public List<Rent> getRents() {
        return rentRepository.findAll();
    }

    public List<Rent> getRentsForVehicle(String vin) {
        return rentRepository.findByRentedVehicle_Vin(vin);
    }

    public List<Rent> getRentsForClient(String login) {
        return rentRepository.findByClient_Login(login);
    }

    public Optional<Rent> endRent(UUID uuid, AcceptedCurrencies currency) throws CurrencyServiceException, NoSuchVehicleException {
        Optional<Rent> rent = rentRepository.findByUuid(uuid);
        if (rent.isPresent()) {
            archivedRentService.addArchivedRent(rent.get(), currency);

            rent.get().setActualFinishedDate(LocalDateTime.now());
            rentRepository.save(rent.get());

            vehicleService.changeAvailability(rent.get().getRentedVehicle().getVin(), true);
        }
        return rent;
    }
}
