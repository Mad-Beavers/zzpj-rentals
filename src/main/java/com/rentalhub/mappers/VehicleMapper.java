package com.rentalhub.mappers;

import com.rentalhub.dto.VehicleDto;
import com.rentalhub.model.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toVehicle(VehicleDto vehicleDto);
    VehicleDto toVehicleDto(Vehicle vehicle);
}