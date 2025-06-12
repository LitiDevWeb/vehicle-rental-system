package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.InspectionDtoOut;
import com.cars.vehiclerentalsystem.entity.Inspection;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.mapper.InspectionMapper;
import com.cars.vehiclerentalsystem.repository.InspectionRepository;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class InspectionService {
    private final InspectionRepository inspectionRepository;
    private final InspectionMapper inspectionMapper;
    private final VehicleRepository vehicleRepository;

    public InspectionService(InspectionRepository inspectionRepository, InspectionMapper inspectionMapper, VehicleRepository vehicleRepository) {
        this.inspectionRepository = inspectionRepository;
        this.inspectionMapper = inspectionMapper;
        this.vehicleRepository = vehicleRepository;

    }

    public List<InspectionDtoOut> planifyInspections() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        Date ninetyDaysAgo = Date.from(LocalDate.now().minusDays(90).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<InspectionDtoOut> plannedInspections = new ArrayList<>();

        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getCreatedAt().before(ninetyDaysAgo)) {
                List<Inspection> recentInspections = inspectionRepository
                        .findByVehicleVehicleIdAndCreatedAtAfter(vehicle.getVehicleId(), ninetyDaysAgo);

                if (recentInspections.isEmpty()) {
                    Inspection inspection = inspectionMapper.createInspectionFromVehicle(vehicle);
                    inspectionRepository.save(inspection);
                    plannedInspections.add(inspectionMapper.toDto(inspection));
                }
            }
        }
        return plannedInspections;
    }





}
