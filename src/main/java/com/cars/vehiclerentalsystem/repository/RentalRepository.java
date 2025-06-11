package com.cars.vehiclerentalsystem.repository;

import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT COUNT(r) FROM Rental r WHERE r.client.clientId = :clientId AND r.status = :status")
    long countActiveRentalsByClientId(@Param("clientId") Integer clientId, @Param("status") RentalStatus status);
}
