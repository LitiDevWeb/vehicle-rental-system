package com.cars.vehiclerentalsystem.mapper;

import com.cars.vehiclerentalsystem.dto.InspectionDtoIn;
import com.cars.vehiclerentalsystem.dto.InspectionDtoOut;
import com.cars.vehiclerentalsystem.entity.Inspection;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.InspectionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InspectionMapper {
    Inspection toEntity(InspectionDtoIn inspectionDtoIn);
    List<Inspection> toEntityList(List<InspectionDtoIn> inspectionDtoIn);
    @Mapping(source = "vehicle.vehicleId", target = "vehicleId")
    InspectionDtoOut toDto(Inspection inspection);
    List<InspectionDtoOut> toDtoList(List<Inspection> inspections);

    default Inspection createInspectionFromVehicle(Vehicle vehicle) {
        Inspection inspection = new Inspection();
        inspection.setVehicle(vehicle);
        inspection.setCreatedAt(new Date());
        inspection.setStatus(InspectionStatus.PLANNED);
        return inspection;
    }
}
