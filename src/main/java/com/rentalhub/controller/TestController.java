package com.rentalhub.controller;

import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.Client;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/admin_test")
    public ResponseEntity<String> testAdminSecuredEndpoint() {
        return ResponseEntity.accepted().body("Secret admin info");
    }

    @GetMapping("/client_test")
    public ResponseEntity<String> testClientSecuredEndpoint() {
        return ResponseEntity.accepted().body("Secret client info");
    }
}
