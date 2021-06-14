package com.rentalhub.repository;

import com.rentalhub.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query("from rents as r where r.uuid = :uuid")
    Optional<Rent> findByUuid(UUID uuid);

    List<Rent> findByClient_Login(String login);

    List<Rent> findByRentedVehicle_Vin(String vin);
}
