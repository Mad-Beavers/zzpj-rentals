package com.rentalhub.repository;

import com.rentalhub.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InMemoryVehicleRepository {
    private Set<Vehicle> vehicles;

    public InMemoryVehicleRepository() {
        vehicles = new HashSet<>();

        vehicles.add(new Vehicle("1HGCM82633A004352", "Ford", "Mondeo",
                4, 2.0, DrivingLicenseCategory.B));

        vehicles.add(new Vehicle("2HGCM82633A004352", "Ford", "Mondeo",
                4, 2.0, DrivingLicenseCategory.B));

    }



    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void editVehicle(Vehicle vehicle) {
        if(vehicles.removeIf(vehicle1 -> vehicle.getVin().equals(vehicle1.getVin()))) {
            vehicles.add(vehicle);
        }
    }
    public void changeAvailability(String vin, boolean isAvailable) {
        vehicles.stream().filter(vehicle -> vehicle.getVin().equals(vin)).findAny().get().setAvailable(isAvailable);
    }


    public Vehicle getVehicle(String vin) {
        if(vehicles.stream().anyMatch(vehicle -> vehicle.getVin().equals(vin))) {
            return vehicles.stream().filter(user -> user.getVin().equals(vin)).findAny().get();
        } else {
            throw new NullPointerException("This user doesn't exists");

        }

    }
}