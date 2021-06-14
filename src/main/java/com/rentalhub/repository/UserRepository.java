package com.rentalhub.repository;

import com.rentalhub.model.Admin;
import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.repository.subRepos.AdminRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository {
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public UserRepository(ClientRepository clientRepository, AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    public Optional<User> findByLogin(String login) {
        Optional<Client> client = clientRepository.findByLogin(login);
        if (client.isPresent()) {
            return client.map(User.class::cast);
        }
        return adminRepository.findByLogin(login).map(User.class::cast);
    }

    public User save(User user) {
        if (user instanceof Client client) {
            return clientRepository.save(client);
        }

        if (user instanceof Admin admin) {
            return adminRepository.save(admin);
        }
        return null;
    }

    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }
}
