package com.cars.vehiclerentalsystem.repository;

import com.cars.vehiclerentalsystem.entity.Inspection;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.InspectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Integer> {
    List<Inspection> findByVehicleVehicleIdAndCreatedAtAfter(Integer vehicleId, Date date);
    List<Inspection> findByVehicleVehicleIdAndStatus(Integer vehicleId, InspectionStatus status);
    @Query("""
    SELECT v FROM Vehicle v 
    WHERE 
        NOT EXISTS (
            SELECT i FROM Inspection i WHERE i.vehicle = v
        )
        OR (
            SELECT MAX(i.createdAt) FROM Inspection i WHERE i.vehicle = v
        ) < :thresholdDate
""")
    List<Vehicle> findVehiclesToInspectBefore(@Param("thresholdDate") Date thresholdDate);
}
