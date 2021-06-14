package com.rentalhub.model;

import com.rentalhub.validators.Login;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Inheritance
public abstract class User {
    private String passwordHash;

    @Login
    @Column(unique = true)
    private String login;

    @Email
    @Column(unique = true)
    private String email;

    public abstract AccessLevel getAccessLevel();

    public User(String passwordHash, String login, String email) {
        this.passwordHash = passwordHash;
        this.login = login;
        this.email = email;
    }
}

