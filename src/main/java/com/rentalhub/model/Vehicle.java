package com.rentalhub.model;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_SEQUENCE")
    @SequenceGenerator(name = "VEHICLE_SEQUENCE", sequenceName = "PUBLIC.VEHICLE_SEQUENCE", allocationSize = 1, schema = "PUBLIC")
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

    @Positive
    private Double dailyLoanPrice;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private DrivingLicenseCategory dlc;

    public Vehicle(String vin, String brand, String model, Boolean available, Integer numberOfSeats, Double engineCapacity, Double dailyLoanPrice, VehicleType vehicleType,DrivingLicenseCategory dlc) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.available = available;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.dailyLoanPrice = dailyLoanPrice;
        this.dlc = dlc;
    }

    public Vehicle(String vin, String brand, String model, Integer numberOfSeats, Double engineCapacity, Double dailyLoanPrice, VehicleType vehicleType, DrivingLicenseCategory dlc) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.available = true;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.dailyLoanPrice = dailyLoanPrice;
        this.dlc = dlc;
    }
}
