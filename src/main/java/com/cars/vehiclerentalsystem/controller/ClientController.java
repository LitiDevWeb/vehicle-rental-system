package com.cars.vehiclerentalsystem.controller;

import com.cars.vehiclerentalsystem.dto.ClientDtoIn;
import com.cars.vehiclerentalsystem.dto.ClientDtoOut;
import com.cars.vehiclerentalsystem.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Clients", description = "API pour la gestion des clients")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client",
            description = "Cette API permet de créer un nouveau client.")

    public ResponseEntity<ClientDtoOut> createClient(@RequestBody ClientDtoIn clientDtoIn){
        ClientDtoOut dtoOut = clientService.addClient(clientDtoIn);

        return ResponseEntity.ok().body(dtoOut);
    }

    @GetMapping
    @Operation(summary = "Récuperer la liste des clients",
            description = "Cette API permet de récuperer la liste des clients.")

    public ResponseEntity<List<ClientDtoOut>> getAllClients(){
        List<ClientDtoOut> dtoOutList = clientService.retrieveClients();
        return ResponseEntity.ok().body(dtoOutList);
    }


}
