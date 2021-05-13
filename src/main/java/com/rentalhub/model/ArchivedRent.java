package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivedRent {
    @Id
    private Long id;
    private UUID uuid;

    private Rent rent;

    @PositiveOrZero
    private Double costsInPln;
    @NotEmpty
    private String currency;
    @PositiveOrZero
    private Double plnToCurrency;
}
