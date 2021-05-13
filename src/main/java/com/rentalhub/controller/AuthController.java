package com.rentalhub.controller;

import com.rentalhub.common.I18n;
import com.rentalhub.dto.AuthRequestDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.exception.AppBaseException;
import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.repository.InMemoryUserRepository;
import com.rentalhub.security.jwt.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rentalhub.common.I18n.USER_NOT_FOUND_ERROR;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private InMemoryUserRepository inMemoryUserRepository; // todo remove it and instead insert user service

    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody @Valid ClientRegistrationDto clientRegistrationDto) {
        Client client = new ModelMapper().map(clientRegistrationDto, Client.class);
        client.setPasswordHash(passwordEncoder.encode(clientRegistrationDto.getPassword()));
        inMemoryUserRepository.addClient(client);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        try {
            User user = inMemoryUserRepository.findUserByLogin(authRequestDto.getLogin()).orElseThrow(() -> new AppBaseException(USER_NOT_FOUND_ERROR));
            if (passwordEncoder.matches(authRequestDto.getPassword(), user.getPasswordHash())) {
                String token = jwtProvider.generateToken(user.getLogin());
                return ResponseEntity.accepted().body(token);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
