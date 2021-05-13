package com.rentalhub.model;

import lombok.*;

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

    public User(String passwordHash, String login, String email) {
        this.passwordHash = passwordHash;
        this.login = login;
        this.email = email;
    }
}

