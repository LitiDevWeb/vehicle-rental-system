package com.cars.vehiclerentalsystem.dto;

import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDtoOut {
    private Integer vehicleId;
    private VehicleStatus status;
    private Double km;
    private Date createdAt;

}
