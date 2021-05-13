package com.rentalhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
