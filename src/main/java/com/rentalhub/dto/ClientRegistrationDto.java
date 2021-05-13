package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.validators.Name;
import com.rentalhub.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public record ClientRegistrationDto(
        @NotEmpty String login,
        @Email String email,
        @Size(min = 8) String password,
        @Name String firstName,
        @Name String secondName,
        @PhoneNumber String phoneNumber,
        Set<DrivingLicenseCategory> drivingLicenseCategories
) {

}
