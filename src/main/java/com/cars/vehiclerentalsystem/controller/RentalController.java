package com.cars.vehiclerentalsystem.controller;

import com.cars.vehiclerentalsystem.dto.RentalDtoIn;
import com.cars.vehiclerentalsystem.dto.RentalDtoOut;
import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rentals")
@Tag(name = "Vehicles", description = "API pour la gestion des vehicules")
@Slf4j

public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping()
    @Operation(summary = "Location d'un vehicule",
            description = "Cette API permet de louer un vehicule.")

    public ResponseEntity<RentalDtoOut> rentVehicle(@RequestBody RentalDtoIn rentalDtoIn ,@RequestParam Integer clientId,
                                                    @RequestParam Integer vehicleId) {
        RentalDtoOut newRental = rentalService.createRental(vehicleId, clientId, rentalDtoIn);
        return ResponseEntity.ok().body(newRental);
    }


}
