package com.cars.vehiclerentalsystem.controller;

import com.cars.vehiclerentalsystem.dto.InspectionDtoOut;
import com.cars.vehiclerentalsystem.service.InspectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inspections")
@Tag(name = "Inspections", description = "API pour la gestion des inspections techniques des vehicules")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping("/planify")
    @Operation(summary = "Effectuer une inspection technique",
            description = "Cette API permet d'effectuer une insepection techniques aux vehicules >90 jrs .")
    public ResponseEntity<List<InspectionDtoOut>> postAllVehiclesToInspect(){
        List<InspectionDtoOut> plannedInspections = inspectionService.planifyInspections();
        if (plannedInspections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(plannedInspections);
    }

    @GetMapping
    @Operation(summary = "Recuperer la liste de vehicules a inspecter",
            description = "Cette API permet de recuperer la liste de vehicules a inspecter.")
    public ResponseEntity<List<InspectionDtoOut>> getAllVehiclesToInspect(){
        List<InspectionDtoOut> plannedInspections = inspectionService.getVehiclesToInspect();
        if (plannedInspections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(plannedInspections);
    }



}
