package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;

import javax.persistence.Id;

public record VehicleDto (
         String vin,
         String brand,
         String model,
         Integer numberOfSeats,
         Double engineCapacity,
         DrivingLicenseCategory dlc) {

}
