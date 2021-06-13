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

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentDto toRentDto(Rent rent);
    List<RentDto> toRentDtoList(List<Rent> rents);
}
