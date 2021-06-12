package com.rentalhub.service;

import com.rentalhub.model.ArchivedRent;
import com.rentalhub.repository.ArchivedRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ArchivedRentService {

    private ArchivedRentRepository repository;

    @Autowired
    public ArchivedRentService(ArchivedRentRepository repository) {
        this.repository = repository;
    }

    public Optional<ArchivedRent> getArchivedRent(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    public List<ArchivedRent> getArchivedRents() {
        return repository.findAll();
    }
}
