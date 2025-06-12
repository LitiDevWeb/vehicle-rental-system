package com.cars.vehiclerentalsystem.entity;


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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;
    private String name;
    private String phoneNumber;
    private Boolean hasUnpaidDebt;
    private Boolean hasUnpaidCautions;
    private Date createdAt;
    @Column(name = "total_penalties")
    private Double totalPenalties;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;

}
