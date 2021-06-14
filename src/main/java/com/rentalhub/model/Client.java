package com.rentalhub.model;

import com.rentalhub.validators.Name;
import com.rentalhub.validators.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "clients")
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQUENCE")
    @SequenceGenerator(name = "CLIENT_SEQUENCE", sequenceName = "PUBLIC.CLIENT_SEQUENCE", allocationSize = 1, schema = "PUBLIC")
    private Long id;

    @Name
    private String firstName;

    @Name
    private String lastName;

    @PhoneNumber
    private String phoneNumber;

    private LocalDateTime registrationDate;
    private Boolean active;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DrivingLicenseCategory> drivingLicenseCategories;

    public Client(String passwordHash, String login, String email, String firstName, String lastName, String phoneNumber, Boolean active, Set<DrivingLicenseCategory> drivingLicenseCategories) {
        super(passwordHash, login, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = LocalDateTime.now();
        this.active = active;
        this.drivingLicenseCategories = drivingLicenseCategories;
    }

    public Client(String passwordHash, String login, String email, String firstName, String lastName, String phoneNumber, Set<DrivingLicenseCategory> drivingLicenseCategories) {
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
