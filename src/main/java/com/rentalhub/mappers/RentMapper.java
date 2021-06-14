package com.rentalhub.mappers;

import com.rentalhub.dto.RentDto;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentMapper {

    @Mapping(source = "client", target = "login", qualifiedByName = "ClientToLogin")
    @Mapping(source = "rentedVehicle", target = "vin", qualifiedByName = "VehicleToVin")
    RentDto toRentDto(Rent rent);

    @Mapping(source = "client", target = "login", qualifiedByName = "ClientToLogin")
    @Mapping(source = "rentedVehicle", target = "vin", qualifiedByName = "VehicleToVin")
    List<RentDto> toRentDtoList(List<Rent> rents);

    @Named("ClientToLogin")
    default String clientToLogin(Client client) {
        return client.getLogin();
    }

    @Named("VehicleToVin")
    default String vehicleToVin(Vehicle vehicle) {
        return vehicle.getVin();
    }
}
