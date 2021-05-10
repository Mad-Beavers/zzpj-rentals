package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {


    private String firstName;
    private String secondName;

    private String phoneNumber;

    private LocalDateTime registrationDate;

    private Boolean active;

    private Set<DrivingLicenseCategory> drivingLicenseCategories;

    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_CLIENT;
    }

}
