package com.rentalhub.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class Client extends User {
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private Boolean active;
    private Set<DrivingLicenseCategory> drivingLicenseCategories;

    public Client(Long id, String passwordHash, String login, String email, String firstName, String secondName, String phoneNumber,
                  LocalDateTime registrationDate, Boolean active, Set<DrivingLicenseCategory> drivingLicenseCategories) {
        super(id, passwordHash, login, email);
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.active = active;
        this.drivingLicenseCategories = drivingLicenseCategories;
    }

    public Client(String passwordHash, String login, String email, String firstName, String secondName, String phoneNumber, LocalDateTime registrationDate, Boolean active, Set<DrivingLicenseCategory> drivingLicenseCategories) {
        super(passwordHash, login, email);
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.active = active;
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
