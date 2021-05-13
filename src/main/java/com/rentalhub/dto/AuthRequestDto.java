package com.rentalhub.dto;

import com.rentalhub.validators.Login;

import javax.validation.constraints.Size;

public record AuthRequestDto(@Login String login, @Size(min = 8) String password) {
}
