package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
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

    private Double costsInPln;
    private String currency;
    private Double plnToCurrency;
}
