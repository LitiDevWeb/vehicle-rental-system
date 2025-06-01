package com.cars.vehiclerentalsystem.mapper;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toEntity(VehicleDtoIn vehicleDtoIn);
    List<Vehicle> toEntityList(List<VehicleDtoIn> vehicleDtoInList);
    @Mapping(source = "vehicleId", target = "vehicleId")
    VehicleDtoOut toDto(Vehicle vehicle);
    List<VehicleDtoOut> toDtoList(List<Vehicle> vehicles);
}
