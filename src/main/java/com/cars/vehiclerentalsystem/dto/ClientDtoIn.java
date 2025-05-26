package com.cars.vehiclerentalsystem.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDtoIn {
    private String name;
    private String phoneNumber;
}
