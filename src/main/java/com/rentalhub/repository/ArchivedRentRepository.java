package com.rentalhub.repository;

import com.rentalhub.model.ArchivedRent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArchivedRentRepository extends JpaRepository<ArchivedRent, Long> {

    Optional<ArchivedRent> findByUuid(UUID uuid);
}
