package com.rentalhub.service;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.dto.RentDto;
import com.rentalhub.exception.CurrencyServiceException;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.RentRepository;
import com.rentalhub.repository.VehicleRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
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

    public Rent addRent(RentDto dto) {
        Optional<Vehicle> vehicle = vehicleService.getVehicle(dto.vin());
        Optional<Client> client = userService.getClient(dto.login());
        if (client.isEmpty() || vehicle.isEmpty()) {
            throw new RuntimeException();
        }
        if (vehicle.get().getAvailable() == false) {
            throw new RuntimeException();
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

    public Optional<Rent> endRent(UUID uuid, AcceptedCurrencies currency) throws CurrencyServiceException {
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
