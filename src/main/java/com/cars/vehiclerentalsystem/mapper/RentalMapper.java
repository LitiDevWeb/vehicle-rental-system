package com.cars.vehiclerentalsystem.mapper;

import com.cars.vehiclerentalsystem.dto.RentalDtoIn;
import com.cars.vehiclerentalsystem.dto.RentalDtoOut;
import com.cars.vehiclerentalsystem.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    Rental toEntity(RentalDtoIn rentalDtoIn);
    List<Rental> toEntityList(List<RentalDtoIn> rentalDtoInList);
    @Mapping(source = "client.clientId", target = "clientId")
    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    RentalDtoOut toDto(Rental rental);
    List<RentalDtoOut> toDtoList(List<Rental> rentalList);
}
