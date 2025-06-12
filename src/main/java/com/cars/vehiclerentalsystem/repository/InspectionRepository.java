package com.cars.vehiclerentalsystem.repository;

import com.cars.vehiclerentalsystem.entity.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {
    List<Inspection> findByVehicleVehicleIdAndCreatedAtAfter(Integer vehicleId, Date date);

}
