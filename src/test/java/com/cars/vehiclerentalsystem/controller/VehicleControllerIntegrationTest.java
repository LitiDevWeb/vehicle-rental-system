package com.cars.vehiclerentalsystem.controller;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Lance l’ensemble du contexte Spring Boot (tous les beans, comme si l’application tournait vraiment)
@SpringBootTest
//Configure automatiquement un bean MockMvc, qui permet de simuler des requêtes HTTP sans lancer un vrai serveur.
@AutoConfigureMockMvc
@Slf4j
public class VehicleControllerIntegrationTest {

    @Autowired
    //Injecte l’outil de test MockMvc, qui permet de simuler les requêtes HTTP vers ton contrôleur amzun postman.
    private MockMvc mockMvc;

    @Autowired
    // Injecte le convertisseur Jackson qui permet de convertir un objet Java ↔ JSON pour envoyer des objets dans le crops de la requete content..ect
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @Rollback
    public void createVehicle_shouldReturnAccepted() throws Exception {
        // Given
        VehicleDtoIn vehicleDtoIn = VehicleDtoIn.builder()
                .status(VehicleStatus.AVAILABLE)
                .km(12000.0)
                .build();

        VehicleDtoOut expectedDtoOut = VehicleDtoOut.builder()
                .status(VehicleStatus.AVAILABLE)
                .km(12000.0)
                .build();

        // When
        String response = mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDtoIn)))
                .andExpect(status().isOk()) // ou isAccepted() si ta méthode utilise HttpStatus.ACCEPTED
                .andReturn()
                .getResponse()
                .getContentAsString();

        VehicleDtoOut resultDto = objectMapper.readValue(response, VehicleDtoOut.class);

        // Then
        log.info("[createVehicle] Result: {}", resultDto);
        assertNotNull(resultDto);
        assertEquals(expectedDtoOut.getStatus(), resultDto.getStatus());
        assertEquals(expectedDtoOut.getKm(), resultDto.getKm());
    }

    @Test
    @Transactional
    @Rollback(true)
    @Order(2)
    public void getAllVehicles_shouldReturnOk() throws Exception {
        String response = mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<?> vehicleList = objectMapper.readValue(response, List.class);

        log.info("[getAllVehicles] List size: {}", vehicleList.size());
        assertNotNull(vehicleList);
        // Tu peux ajuster l’assertion si tu as déjà des données injectées (data.sql, etc.)
    }
}





