package com.cars.vehiclerentalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDtoIn {
    private Integer clientId;
    private Integer vehicleId;
    private Date startDate;
    private Date endDate;
}
