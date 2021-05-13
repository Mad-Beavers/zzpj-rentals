package com.rentalhub.mappers;

import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(ClientRegistrationDto clientRegistrationDto);
}
