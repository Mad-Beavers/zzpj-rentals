package com.rentalhub.mappers;

import com.rentalhub.dto.ArchivedRentDto;
import com.rentalhub.model.ArchivedRent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArchivedRentMapper {

    ArchivedRentDto toArchivedRentDto(ArchivedRent archivedRent);
}
