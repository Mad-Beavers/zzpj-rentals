package com.rentalhub.model;

import com.rentalhub.validators.Name;
import com.rentalhub.validators.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "clients")
public class Client extends User {

    @Name
    private String firstName;

    @Name
    private String lastName;

    @PhoneNumber
    private String phoneNumber;

    private LocalDateTime registrationDate;
    private Boolean active;


    @ElementCollection
    private Map<DrivingLicenseCategory, LocalDateTime> drivingLicenseCategories;

    public Client(String passwordHash, String login, String email, String firstName, String lastName, String phoneNumber, Boolean active, Map<DrivingLicenseCategory,LocalDateTime> drivingLicenseCategories) {
        super(passwordHash, login, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = LocalDateTime.now();
        this.active = active;
        this.drivingLicenseCategories = drivingLicenseCategories;
    }

    public Client(String passwordHash, String login, String email, String firstName, String lastName, String phoneNumber, Map<DrivingLicenseCategory,LocalDateTime> drivingLicenseCategories) {
        super(passwordHash, login, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = LocalDateTime.now();
        this.active = true;
        this.drivingLicenseCategories = drivingLicenseCategories;
    }


    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_CLIENT;
    }


    @PrePersist
    public void prePersist() {
        this.registrationDate = LocalDateTime.now();
    }

}
