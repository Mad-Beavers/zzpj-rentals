package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    private Long id;

    private String passwordHash;
    private String login;
    private String email;

    public abstract AccessLevel getAccessLevel();
}
