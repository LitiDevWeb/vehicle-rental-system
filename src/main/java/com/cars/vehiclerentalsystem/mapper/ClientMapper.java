package com.cars.vehiclerentalsystem.mapper;

import com.cars.vehiclerentalsystem.dto.ClientDtoIn;
import com.cars.vehiclerentalsystem.dto.ClientDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientDtoIn clientDtoIn);
    List<Client> toEntityList(List<ClientDtoIn> clientDtoIn);
    @Mapping(source = "clientId", target = "clientId")
    ClientDtoOut toDto(Client client);
    List<ClientDtoOut> toDtoList(List<Client> clients);
}
