package com.cars.vehiclerentalsystem.repository;

import com.cars.vehiclerentalsystem.entity.Inspection;
import com.cars.vehiclerentalsystem.enums.InspectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {
    List<Inspection> findByVehicleVehicleIdAndCreatedAtAfter(Integer vehicleId, Date date);
    List<Inspection> findByVehicleVehicleIdAndStatus(Integer vehicleId, InspectionStatus status);
}
