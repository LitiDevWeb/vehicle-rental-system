package com.cars.vehiclerentalsystem.dto;

import com.cars.vehiclerentalsystem.enums.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalDtoOut {
    private Integer rentalId;
    private Integer clientId;
    private Integer vehicleId;
    private RentalStatus status;
    private Date startDate;
    private Date endDate;
}
