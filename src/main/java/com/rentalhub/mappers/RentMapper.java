package com.rentalhub.mappers;

import com.rentalhub.dto.RentDto;
import com.rentalhub.model.Rent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RentMapper {
    RentDto toRentDto(Rent rent);
}
