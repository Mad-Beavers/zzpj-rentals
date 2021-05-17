package com.rentalhub.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "admins")
public class Admin extends User {

    public Admin(String passwordHash, String login, String email) {
        super(passwordHash, login, email);
    }

    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_ADMIN;
    }

}
