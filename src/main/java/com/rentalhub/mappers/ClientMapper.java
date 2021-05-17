package com.rentalhub.mappers;

import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientInformationDto;
import com.rentalhub.dto.ClientRegistrationDto;
import com.rentalhub.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(ClientRegistrationDto clientRegistrationDto);
    Client toClient(ClientDto clientDto);
    ClientInformationDto toClientInformationDto(Client client);
}
