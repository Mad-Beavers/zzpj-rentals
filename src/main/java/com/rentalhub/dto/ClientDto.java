package com.rentalhub.dto;

import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.validators.Name;
import com.rentalhub.validators.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public record ClientDto (
        @NotEmpty String login,
        @Email String email,
        @Name String firstName,
        @Name String secondName,
        @PhoneNumber String phoneNumber,
        Map<DrivingLicenseCategory, LocalDateTime> drivingLicenseCategories
){
}
