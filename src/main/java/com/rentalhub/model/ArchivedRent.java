package com.rentalhub.model;

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

    @NotEmpty
    private Boolean delayed;

    @PositiveOrZero
    private Double plnToCurrency;

    @NotEmpty
    private int rentRating;

    public ArchivedRent(UUID uuid, Rent rent, Double costInPln, String currency, Double plnToCurrency, int rentRating) {
        this.uuid = uuid;
        this.rent = rent;
        this.rentRating = rentRating;
        this.costInPln = costInPln;
        this.currency = currency;
        this.plnToCurrency = plnToCurrency;
    }
}
