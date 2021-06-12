package com.rentalhub.mappers;

import com.rentalhub.dto.RentDto;
import com.rentalhub.exception.RentMappingException;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import com.rentalhub.service.UserService;
import com.rentalhub.service.VehicleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class RentMapper {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    public abstract RentDto toRentDto(Rent rent);

    @Mapping(source = "vin", target = "rentedVehicle", qualifiedByName = "vinToVehicle")
    @Mapping(source = "login", target = "client", qualifiedByName = "loginToClient")
    public abstract Rent toRent(RentDto dto) throws RentMappingException;

    @Named("vinToVehicle")
    public Vehicle vinToVehicle(String vin) throws RentMappingException {
        Optional<Vehicle> vehicle = vehicleService.getVehicle(vin);
        if (vehicle.isEmpty()) {
            throw new RentMappingException("No such vehicle found");
        }
        return vehicle.get();
    }

    @Named("loginToClient")
    public Client loginToClient(String login) throws RentMappingException {
        Optional<Client> client = userService.getClient(login);
        if (client.isEmpty()) {
            throw new RentMappingException("No such client found");
        }
        return client.get();
    }
}
