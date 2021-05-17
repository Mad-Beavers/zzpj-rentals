package com.rentalhub.service;

import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.repository.UserRepository;
import com.rentalhub.repository.subRepos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public UserService(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Client editClient(Client client) {
        Optional<Client> optionalClient = clientRepository.findByLogin(client.getLogin());
        if (optionalClient.isPresent()) {
            client.setId(optionalClient.get().getId());
            return clientRepository.save(client);
        }
        return null;
    }

    public Client changeClientActivity(String login, boolean isActive) {
        Optional<Client> optionalClient = clientRepository.findByLogin(login);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setActive(isActive);
            return clientRepository.save(client);
        }
        return null;
    }

    public Optional<Client> getClient(String login) {
        return clientRepository.findByLogin(login);
    }

}
