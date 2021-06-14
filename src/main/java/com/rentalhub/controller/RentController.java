package com.rentalhub.controller;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.dto.RentDto;
import com.rentalhub.exception.*;
import com.rentalhub.mappers.RentMapper;
import com.rentalhub.model.Rent;
import com.rentalhub.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    private final RentService service;
    private final RentMapper mapper;

    @Autowired
    public RentController(RentService service, RentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/createRent")
    public ResponseEntity<Rent> createRent(@RequestBody RentDto dto) {
        try {
            return ResponseEntity.ok(service.addRent(dto));
        } catch (InsufficientClientDlcException | UnavailableVehicleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NoSuchClientException | NoSuchVehicleException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RentDto> getRent(@PathVariable String uuid) {
        Optional<Rent> result = service.getRent(UUID.fromString(uuid));
        return result.map(rent -> ResponseEntity.ok().body(mapper.toRentDto(rent)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/forVehicle/{vin}")
    public ResponseEntity<List<RentDto>> getRentForVehicle(@PathVariable String vin) {
        List<Rent> result = service.getRentsForVehicle(vin);
        return ResponseEntity.ok().body(mapper.toRentDtoList(result));
    }

    @GetMapping("/forClient/{login}")
    public ResponseEntity<List<RentDto>> getRentForClient(@PathVariable String login) {
        List<Rent> result = service.getRentsForClient(login);
        return ResponseEntity.ok().body(mapper.toRentDtoList(result));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RentDto>> getRents() {
        List<Rent> result = service.getRents();
        return ResponseEntity.ok().body(mapper.toRentDtoList(result));
    }

    @DeleteMapping("/close/{uuid}/{currencyAbbrev}")
    public ResponseEntity<RentDto> endRent(@PathVariable String uuid, @PathVariable String currencyAbbrev) throws NoSuchVehicleException, CurrencyServiceException {
        AcceptedCurrencies currency;
        try {
            currency = AcceptedCurrencies.valueOf(currencyAbbrev);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

        Optional<Rent> result;
        try {
            result = service.endRent(UUID.fromString(uuid), currency);
        } catch (CurrencyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return result.map(rent -> ResponseEntity.ok().body(mapper.toRentDto(rent)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
