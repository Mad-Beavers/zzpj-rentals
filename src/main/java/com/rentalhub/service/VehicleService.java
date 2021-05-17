package com.rentalhub.service;


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

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void editVehicle(Vehicle vehicle) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vehicle.getVin());
        if (optionalVehicle.isPresent()) {
            vehicle.setId(optionalVehicle.get().getId());
            vehicleRepository.save(vehicle);
        }
    }

    public void changeAvailability(String vin, boolean available) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            vehicle.setAvailable(available);
        }
    }

    public Optional<Vehicle> getVehicle(String vin) {
        return vehicleRepository.findByVin(vin);
    }


}
