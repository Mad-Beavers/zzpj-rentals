package com.rentalhub.service;


import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.InMemoryVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleService {

    @Autowired
    private InMemoryVehicleRepository inMemoryVehicleRepository;

    public void addVehicle(Vehicle vehicle) {
        inMemoryVehicleRepository.addVehicle(vehicle);
    }

    public void editVehicle(Vehicle vehicle) {
        inMemoryVehicleRepository.editVehicle(vehicle);
    }

    public void changeAvailability(String vin, boolean isActive){
        inMemoryVehicleRepository.changeAvailability(vin, isActive);
    }

    public Vehicle getVehicle(String vin) {
        return inMemoryVehicleRepository.getVehicle(vin);
    }



}
