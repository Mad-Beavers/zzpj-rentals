package com.rentalhub.service;

import com.rentalhub.dto.RentDto;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.RentRepository;
import com.rentalhub.repository.VehicleRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RentService {

    private final RentRepository rentRepository;
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public RentService(RentRepository rentRepository, VehicleRepository vehicleRepository, ClientRepository clientRepository) {
        this.rentRepository = rentRepository;
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
    }

    public Rent addRent(RentDto dto) {
        Optional<Client> client = clientRepository.findByLogin(dto.login());
        Optional<Vehicle> vehicle = vehicleRepository.findByVin(dto.vin());
        if (!client.isPresent() || !vehicle.isPresent()) {
            throw new RuntimeException();
        }
        Rent rent = new Rent(vehicle.get(), client.get(), dto.startDate(), dto.declaredFinishedDate(), dto.actualFinishedDate());
        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(UUID uuid) {
        return rentRepository.findByUuid(uuid);
    }

    public List<Rent> getRents() {
        return rentRepository.findAll();
    }
}
