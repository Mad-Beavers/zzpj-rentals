package com.rentalhub.dto;


import com.rentalhub.model.DrivingLicenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationDto {
    private String login;
    private String email;
    private String password;

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Set<DrivingLicenseCategory> drivingLicenseCategories;
}
