package com.rentalhub.repository;

import com.rentalhub.model.Admin;
import com.rentalhub.model.Client;
import com.rentalhub.model.DrivingLicenseCategory;
import com.rentalhub.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InMemoryUserRepository {
    private Set<User> users;

    public InMemoryUserRepository() {
        users = new HashSet<>();

        users.add(new Client(0L, "$2a$10$C6.BNIYCdCziE8Etd8VSZelwoOWP.x1qamPjRuXuZ.AclbqzIs1xy",
                "lolek", "login@gmail.com", "Lolek", "Bolek", "+48123123123",
                LocalDateTime.now(), true, Set.of(DrivingLicenseCategory.B, DrivingLicenseCategory.B1)));

        users.add(new Admin(1L, "$2a$10$C6.BNIYCdCziE8Etd8VSZelwoOWP.x1qamPjRuXuZ.AclbqzIs1xy",
                "tola", "tola@gmail.com"));
    }

    public Optional<User> findUserByLogin(String login) {
        return this.users.stream().filter(user -> user.getLogin().equals(login)).findAny();
    }

    public void addClient(User user) {
        this.users.add(user);
    }
}
