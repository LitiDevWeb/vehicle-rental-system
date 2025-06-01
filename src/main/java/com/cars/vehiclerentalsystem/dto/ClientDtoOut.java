package com.cars.vehiclerentalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDtoOut {
    private Integer clientId;
    private String name;
    private String phoneNumber;
    private Boolean hasUnpaidDebt;
    private Boolean hasUnpaidCautions;
    private Date createdAt;
}
