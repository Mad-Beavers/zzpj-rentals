package com.rentalhub.mappers;

import com.rentalhub.dto.RentDto;
import com.rentalhub.model.Rent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentMapper {

    RentDto toRentDto(Rent rent);
    List<RentDto> toRentDtoList(List<Rent> rents);
}
