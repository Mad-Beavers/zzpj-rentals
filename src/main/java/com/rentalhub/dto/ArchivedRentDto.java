package com.rentalhub.dto;

import com.rentalhub.model.Rent;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public record ArchivedRentDto (
    @NotEmpty Long id,
    @NotEmpty UUID uuid,
    @NotEmpty Rent rent,
    @NotEmpty Double costInPln,
    @NotEmpty String currency,
    @NotEmpty Double plnToCurrency
) {
}
