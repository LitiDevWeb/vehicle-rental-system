package com.cars.vehiclerentalsystem.dto;

import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDtoIn {
    private VehicleStatus status;
    private Double km;
}
