package com.rentalhub.service;

import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.repository.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;

    public void addUser(User user) {
        inMemoryUserRepository.addClient(user);
    }

    public void editClient(Client client) {
        inMemoryUserRepository.editUser(client);
    }

    public void changeActivity(String login, boolean isActive){
        inMemoryUserRepository.changeActivity(login, isActive);
    }

    public Client getClient(String login) {
        return inMemoryUserRepository.getUser(login);
    }

}
