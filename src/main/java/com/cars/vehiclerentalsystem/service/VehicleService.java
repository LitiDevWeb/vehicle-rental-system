package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.mapper.VehicleMapper;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper){
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleDtoOut addVehicle(VehicleDtoIn vehicleDtoIn){
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDtoIn);

        vehicle.setCreatedAt(new Date());

        Vehicle saveVehicle = vehicleRepository.save(vehicle);
        System.out.println("Saved Vehicle ID: " + saveVehicle.getVehicleId());

        return vehicleMapper.toDto(saveVehicle);
    }

    public List<VehicleDtoOut> retrieveVehicles(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicleMapper.toDtoList(vehicles);
    }

    public VehicleDtoOut updateStatusVehicle(Integer id, VehicleDtoIn vehicleDtoIn){
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isEmpty()) {
            throw new EntityNotFoundException("Véhicule avec l'ID " + id + " introuvable.");
        }

        Vehicle vehicle = optionalVehicle.get();
        vehicle.setStatus(vehicleDtoIn.getStatus());

        Vehicle updateVehicule = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(updateVehicule);

    }
}
