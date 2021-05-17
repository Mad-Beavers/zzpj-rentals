package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "archived_rents")
public class ArchivedRent {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;

    @OneToOne
    private Rent rent;

    @PositiveOrZero
    private Double costInPln;

    @NotEmpty
    private String currency;

    @PositiveOrZero
    private Double plnToCurrency;
}
