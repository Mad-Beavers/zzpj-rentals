package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.validators.Name;
import com.rentalhub.validators.Password;
import com.rentalhub.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Valid
public record ClientRegistrationDto(
        @NotEmpty String login,
        @Email String email,
        @Password String password,
        @Name String firstName,
        @Name String secondName,
        @PhoneNumber String phoneNumber,
        Map<DrivingLicenseCategory, LocalDateTime> drivingLicenseCategories
) {

}
