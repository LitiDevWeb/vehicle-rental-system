package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import com.cars.vehiclerentalsystem.mapper.VehicleMapper;
import com.cars.vehiclerentalsystem.repository.ClientRepository;
import com.cars.vehiclerentalsystem.repository.RentalRepository;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final ClientRepository clientRepository;
    private final RentalRepository rentalRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, ClientRepository clientRepository, RentalRepository rentalRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.clientRepository = clientRepository;
        this.rentalRepository = rentalRepository;
    }

    public VehicleDtoOut addVehicle(VehicleDtoIn vehicleDtoIn){
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDtoIn);

        vehicle.setCreatedAt(new Date());

        Vehicle saveVehicle = vehicleRepository.save(vehicle);
        System.out.println("Saved Vehicle ID: " + saveVehicle.getVehicleId());

        return vehicleMapper.toDto(saveVehicle);
    }

    public List<VehicleDtoOut> getVehicles(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicleMapper.toDtoList(vehicles);
    }

    public VehicleDtoOut updateStatusVehicle(Integer id, VehicleDtoIn vehicleDtoIn) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule avec l'ID " + id + " introuvable."));

        vehicle.setStatus(vehicleDtoIn.getStatus());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(updatedVehicle);
    }

    private void applyPenalty(Client client, double amount) {
        double currentTotal = client.getTotalPenalties() != null ? client.getTotalPenalties() : 0;
        client.setTotalPenalties(currentTotal + amount);
        clientRepository.save(client);
    }

    public VehicleDtoOut returnVehicle(Integer rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Location de ce vehicule est introuvable."));

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = rental.getEndDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        if (endDate.isBefore(currentDate.minusDays(3))) {
            applyPenalty(rental.getClient(), 100.0);
        }

        rental.setStatus(RentalStatus.INACTIVE);
        rentalRepository.save(rental);

        return vehicleMapper.toDto(rental.getVehicle());
    }


}
