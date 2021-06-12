package com.rentalhub.controller;

import com.rentalhub.dto.ArchivedRentDto;
import com.rentalhub.mappers.ArchivedRentMapper;
import com.rentalhub.model.ArchivedRent;
import com.rentalhub.service.ArchivedRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/archived-rents")
public class ArchivedRentController {

    private final ArchivedRentService service;
    private final ArchivedRentMapper mapper;

    @Autowired
    public ArchivedRentController(ArchivedRentService service, ArchivedRentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ArchivedRentDto> getArchivedRent(@PathVariable String uuid) {
        Optional<ArchivedRent> result = service.getArchivedRent(UUID.fromString(uuid));
        return result.map(archivedRent -> ResponseEntity.ok().body(mapper.toArchivedRentDto(archivedRent)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
