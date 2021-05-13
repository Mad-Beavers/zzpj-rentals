package com.rentalhub.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Admin extends User {

    public Admin(Long id, String passwordHash, String login, String email) {
        super(id, passwordHash, login, email);
    }

    public Admin(String passwordHash, String login, String email) {
        super(passwordHash, login, email);
    }

    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_ADMIN;
    }
}
