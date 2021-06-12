package com.rentalhub.dto;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

public record RentDto (
        @NotEmpty UUID uuid,
        @NotEmpty String vin,
        @NotEmpty String login,
        @NotEmpty LocalDateTime startDate,
        @NotEmpty LocalDateTime declaredFinishedDate,
        LocalDateTime actualFinishedDate
) {
}
