package com.rentalhub.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "admins")
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_SEQUENCE")
    @SequenceGenerator(name = "ADMIN_SEQUENCE", sequenceName = "PUBLIC.ADMIN_SEQUENCE", allocationSize = 1, schema = "PUBLIC")
    private Long id;

    public Admin(String passwordHash, String login, String email) {
        super(passwordHash, login, email);
    }

    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_ADMIN;
    }

}
