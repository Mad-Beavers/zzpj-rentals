package com.rentalhub.model;

import lombok.*;

import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private Long id;
    private String Vin;
    private String brand;
    private String model;
    private Double engineCapacity;
    private DrivingLicenseCategory dlc;
}
