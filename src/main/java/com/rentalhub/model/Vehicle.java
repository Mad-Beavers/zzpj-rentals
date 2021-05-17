package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private Long id;
    @NotEmpty
    private String vin;
    private String brand;
    private String model;
    private Boolean available;
    private Integer numberOfSeats;
    private Double engineCapacity;
    private DrivingLicenseCategory dlc;

    public Vehicle(String vin, String brand, String model,
                   Integer numberOfSeats, Double engineCapacity, DrivingLicenseCategory dlc) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.available = true;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.dlc = dlc;
    }
}
