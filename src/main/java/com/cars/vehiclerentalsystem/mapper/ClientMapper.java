package com.cars.vehiclerentalsystem.mapper;

import com.cars.vehiclerentalsystem.dto.ClientDtoIn;
import com.cars.vehiclerentalsystem.dto.ClientDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientDtoIn clientDtoIn);
    ClientDtoOut toDto(Client client);
}
