package com.cars.vehiclerentalsystem.controller;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicles", description = "API pour la gestion des vehicules")
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un nouveau véhicule",
            description = "Cette API permet d'ajout d'un nouveau véhicle")

    public ResponseEntity<VehicleDtoOut> addVehicle(@RequestBody VehicleDtoIn vehicleDtoIn){
        VehicleDtoOut dtoOut = vehicleService.addVehicle(vehicleDtoIn);


        return ResponseEntity.ok().body(dtoOut);
    }

    @GetMapping
    @Operation(summary = "Récuperer la liste des véhicules",
            description = "Cette API permet de récuperer la liste des véhicules.")

    public ResponseEntity<List<VehicleDtoOut>> getAllVehicles(){
        List<VehicleDtoOut> dtoOutList = vehicleService.getVehicles();
        return ResponseEntity.ok().body(dtoOutList);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Mettre a jour le status d'un véhicle",
            description = "Cette API permet de mettre a jour le status d'un véhicule.")

    public ResponseEntity<VehicleDtoOut> updateVehicle(@PathVariable("id") Integer id, @RequestBody VehicleDtoIn vehicleDtoIn){
        VehicleDtoOut updatedDto = vehicleService.updateStatusVehicle(id, vehicleDtoIn);
        return ResponseEntity.ok().body(updatedDto);
    }





}
