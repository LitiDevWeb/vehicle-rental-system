package com.cars.vehiclerentalsystem.entity;

import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;
    private VehicleStatus status;
    private Double km;
    private Date createdAt;

    @OneToMany(mappedBy="vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inspection> inspections;
}
