package com.rentalhub.service;

import com.rentalhub.model.User;
import com.rentalhub.repository.InMemoryUserRepository;
import com.rentalhub.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository; // todo remove it and instead insert user service

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = inMemoryUserRepository.findUserByLogin(login);
        return CustomUserDetails.fromUserEntToCustomUserDetails(user.orElseThrow(() -> new UsernameNotFoundException("there is no user with such login")));
    }

}
