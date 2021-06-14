package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;

import javax.validation.constraints.NotEmpty;

public record VehicleDto (
         @NotEmpty String vin,
         String brand,
         String model,
         Integer numberOfSeats,
         Double engineCapacity,
         Boolean available,
         DrivingLicenseCategory dlc) {

}
