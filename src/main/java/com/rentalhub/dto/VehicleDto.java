package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

public record VehicleDto (
         @NotEmpty String vin,
         String brand,
         String model,
         Boolean available,
         Integer numberOfSeats,
         Double engineCapacity,
         DrivingLicenseCategory dlc) {

}
