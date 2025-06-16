package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.VehicleDtoIn;
import com.cars.vehiclerentalsystem.dto.VehicleDtoOut;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import com.cars.vehiclerentalsystem.mapper.VehicleMapper;
import com.cars.vehiclerentalsystem.repository.ClientRepository;
import com.cars.vehiclerentalsystem.repository.RentalRepository;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //permet d'utiliser mockito de junit5
public class VehicleServiceTest {

    //Mock: Ils permettent de simuler le comportement des dépendances sans faire d’appel réel à la BDD ou au mapper.
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleMapper vehicleMapper;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private RentalRepository rentalRepository;

    //Crée une instance de VehicleService et injecte dedans les mocks automatiquement.
    @InjectMocks
    private VehicleService vehicleService;

    private VehicleDtoIn vehicleDtoIn;
    private Vehicle vehicle;
    private VehicleDtoOut vehicleDtoOut;

    @BeforeEach
    void setUp() {
        // DTO d’entrée via builder afin d'Initialiser les objets communs à tous les tests.
        vehicleDtoIn = VehicleDtoIn.builder()
                .status(VehicleStatus.AVAILABLE)
                .km(25456.4)
                .build();

        vehicle = Vehicle.builder()
                .createdAt(Date.from(Instant.now()))
                .status(VehicleStatus.AVAILABLE)
                .km(25456.4)
                .build();

        vehicleDtoOut = VehicleDtoOut.builder()
                .createdAt(Date.from(Instant.now()))
                .status(VehicleStatus.AVAILABLE)
                .km(25456.4)
                .build();

    }

    @Test
    //<methodUnderTest>_<expectedBehavior>_<whenCondition>
    void addVehicle_shouldReturnVehicleDtoOut_whenVehicleInputIsValid() {
        //Phase 1 : Préparation
        //Simule que le mapper retourne un Vehicle quand on lui passe un VehicleDtoIn
        when(vehicleMapper.toEntity(vehicleDtoIn)).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> {
            Vehicle saved = invocation.getArgument(0);
            saved.setVehicleId(1); // simulate l’ID généré par la base
            return saved;
        });
        when(vehicleMapper.toDto(any(Vehicle.class))).thenReturn(vehicleDtoOut);

        //Phase 2 : Appel de la méthode
        VehicleDtoOut result = vehicleService.addVehicle(vehicleDtoIn);

        //Phase 3 : Vérification (Assert)
        assertNotNull(result);
        assertEquals(25456.4, result.getKm());
        assertEquals(VehicleStatus.AVAILABLE, result.getStatus());

    }

}
