package com.rentalhub.repository;

import com.rentalhub.model.ArchivedRent;
import com.rentalhub.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArchivedRentRepository extends JpaRepository<ArchivedRent, Long> {

    Optional<ArchivedRent> findByUuid(UUID uuid);
    List<ArchivedRent> findArchivedRentByRent_Client(Client client);
}
