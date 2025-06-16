package com.cars.vehiclerentalsystem.dto;

import com.cars.vehiclerentalsystem.enums.InspectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectionDtoIn {
    private Integer vehicleId;
    private InspectionStatus status;
    private Date createdAt;
}
