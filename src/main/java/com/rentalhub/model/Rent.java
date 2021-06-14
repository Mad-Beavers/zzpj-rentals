package com.rentalhub.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "rents")
public class Rent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private final UUID uuid = UUID.randomUUID();

    @ManyToOne
    private Vehicle rentedVehicle;

    @ManyToOne
    private Client client;

    @NotNull
    private LocalDateTime startDate;

    private boolean accepted;

    @NotNull
    private LocalDateTime declaredFinishedDate;

    private LocalDateTime actualFinishedDate;

    public Rent(Vehicle rentedVehicle, Client client, LocalDateTime startDate,
                LocalDateTime declaredFinishedDate, LocalDateTime actualFinishedDate) {
        this.rentedVehicle = rentedVehicle;
        this.client = client;
        this.startDate = startDate;
        this.accepted = false;
        this.declaredFinishedDate = declaredFinishedDate;
        this.actualFinishedDate = actualFinishedDate;
    }

    public Rent(Vehicle rentedVehicle, Client client, LocalDateTime startDate,
                LocalDateTime declaredFinishedDate) {
        this.rentedVehicle = rentedVehicle;
        this.client = client;
        this.startDate = startDate;
        this.accepted = false;
        this.declaredFinishedDate = declaredFinishedDate;
    }
}
