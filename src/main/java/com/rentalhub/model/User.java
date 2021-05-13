package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public abstract class User {
    @Id
    private Long id;

    private String passwordHash;
    private String login;
    private String email;

    public abstract AccessLevel getAccessLevel();

    public User() {
    }

    public User(Long id, String passwordHash, String login, String email) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.login = login;
        this.email = email;
    }

    public User(String passwordHash, String login, String email) {
        this.passwordHash = passwordHash;
        this.login = login;
        this.email = email;
    }
}

