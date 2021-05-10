package com.rentalhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {
    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.ROLE_ADMIN;
    }
}
