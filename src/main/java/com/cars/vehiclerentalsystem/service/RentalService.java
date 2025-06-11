package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.RentalDtoIn;
import com.cars.vehiclerentalsystem.dto.RentalDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import com.cars.vehiclerentalsystem.mapper.RentalMapper;
import com.cars.vehiclerentalsystem.repository.ClientRepository;
import com.cars.vehiclerentalsystem.repository.RentalRepository;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final RentalMapper rentalMapper;

    public RentalService(RentalRepository rentalRepository, ClientRepository clientRepository, VehicleRepository vehicleRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
        this.rentalMapper = rentalMapper;
    }

    //RentalDtoOut c'est les donnees qu'on renvoie au client apres traitement (sortie), en entree (params: RentalDtoIn) du client (formulaire)
    public RentalDtoOut createRental(Integer vehicleId ,Integer clientId, RentalDtoIn rentalDtoIn) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client doesn't exist"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle does not exist"));

        long activeRentalCount = rentalRepository.countActiveRentalsByClientId(clientId, RentalStatus.ACTIVE);
        if (activeRentalCount >= 2) {
            throw new IllegalStateException("Ce client a déjà 2 locations actives.");
        }
        // on transforme RentalDtoIn en une vraie entité Rental, prête à être enregistrée en bdd.
        Rental rental = rentalMapper.toEntity(rentalDtoIn);

        rental.setClient(client);
        rental.setVehicle(vehicle);
        rental.setStatus(RentalStatus.ACTIVE);
        rental.setCreatedAt(new Date());


        Rental savedRental = rentalRepository.save(rental);

        //On ne retournes pas directement l'entité Rental, car : Tu veux renvoyer uniquement les champs néc à afficher côté frontend (comme id, date début, statut...)
        return rentalMapper.toDto(savedRental);
    }
}

