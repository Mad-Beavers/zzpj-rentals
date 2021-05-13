package com.rentalhub.model;

import lombok.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    @Id
    private Long id;
    private UUID uuid;
    private Vehicle rentedVehicle;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime declaredFinishedDate;
    private LocalDateTime actualFinishedDate;
}
