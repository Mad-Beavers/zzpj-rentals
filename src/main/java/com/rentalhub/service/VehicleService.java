package com.rentalhub.service;


import com.rentalhub.exception.NoSuchVehicleException;
import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        Vehicle save = vehicleRepository.saveAndFlush(vehicle);
        return save;
    }

    public Vehicle editVehicle(Vehicle vehicle) throws NoSuchVehicleException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vehicle.getVin());

        if (optionalVehicle.isEmpty()) {
            throw new NoSuchVehicleException("Vehicle with vin " + vehicle.getVin() + " not found");
        }

        vehicle.setId(optionalVehicle.get().getId());
        return vehicleRepository.save(vehicle);

    }

    public Vehicle changeAvailability(String vin, boolean available) throws NoSuchVehicleException {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);

        if (optionalVehicle.isEmpty()) {
            throw new NoSuchVehicleException("Vehicle with vin " + vin + " not found");
        }

        Vehicle vehicle = optionalVehicle.get();
        vehicle.setAvailable(available);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Optional<Vehicle> getVehicle(String vin) {
        return vehicleRepository.findByVin(vin);
    }

}
