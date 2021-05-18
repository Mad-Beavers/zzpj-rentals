package com.rentalhub.controller;

import com.rentalhub.dto.AuthRequestDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.mappers.ClientMapper;
import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.repository.UserRepository;
import com.rentalhub.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.rentalhub.common.I18n.USER_NOT_FOUND_ERROR;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final ClientMapper clientMapper;

    @Autowired
    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, ClientMapper clientMapper) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.clientMapper = clientMapper;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody @Valid ClientRegistrationDto clientRegistrationDto) {
        Client client = clientMapper.toClient(clientRegistrationDto);
        client.setPasswordHash(passwordEncoder.encode(clientRegistrationDto.password()));
        userRepository.save(client);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        Optional<User> optionalUser = userRepository.findByLogin(authRequestDto.login());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND_ERROR);
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(authRequestDto.password(), user.getPasswordHash())) {
            String token = jwtProvider.generateToken(user.getLogin());
            return ResponseEntity.accepted().body(token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
