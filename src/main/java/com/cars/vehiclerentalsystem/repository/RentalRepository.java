package com.cars.vehiclerentalsystem.repository;

import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    List<Rental> findRentalsByClient_ClientId(Integer clientId);
    List<Rental> findRentalsByClient_ClientIdAndStatus(Integer clientId,RentalStatus status);
    List<Rental> findByStatusAndEndDateBefore(RentalStatus status, Date cutoffDate);

    @Query("SELECT COUNT(r) FROM Rental r WHERE r.client.clientId = :clientId AND r.status = :status")
    long countActiveRentalsByClientId(@Param("clientId") Integer clientId, @Param("status") RentalStatus status);

    //s’il existe au moins une location précédente (Rental r), pour laquelle : Le client a le même clientId, Le véhicule a le même vehicleId, La date de début de location (r.startDate) est postérieure à :minStartDate =15jrs
    @Query("SELECT COUNT(r) > 0 FROM Rental r WHERE r.client.clientId = :clientId AND r.vehicle.vehicleId = :vehicleId AND r.startDate > :minStartDate")
    boolean hasSameVehicleRentedByClientInLast15Days(
            @Param("clientId") Integer clientId,
            @Param("vehicleId") Integer vehicleId,
            @Param("minStartDate") Date minStartDate);


}
