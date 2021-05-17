package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String vin;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    private Boolean available;

    @Positive
    private Integer numberOfSeats;

    @Positive
    private Double engineCapacity;

    @Enumerated(EnumType.STRING)
    private DrivingLicenseCategory dlc;

    public Vehicle(String vin, String brand, String model, Boolean available,
                   Integer numberOfSeats, Double engineCapacity, DrivingLicenseCategory dlc) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.available = available;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.dlc = dlc;
    }

    public Vehicle(String vin, String brand, String model, Integer numberOfSeats, Double engineCapacity, DrivingLicenseCategory dlc) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.dlc = dlc;
        this.available = true;
    }
}
