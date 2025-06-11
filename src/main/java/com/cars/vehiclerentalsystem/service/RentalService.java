package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.RentalDtoIn;
import com.cars.vehiclerentalsystem.dto.RentalDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import com.cars.vehiclerentalsystem.enums.VehicleStatus;
import com.cars.vehiclerentalsystem.mapper.RentalMapper;
import com.cars.vehiclerentalsystem.repository.ClientRepository;
import com.cars.vehiclerentalsystem.repository.RentalRepository;
import com.cars.vehiclerentalsystem.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        if (vehicle.getStatus() == VehicleStatus.RENTED ||
                vehicle.getStatus() == VehicleStatus.UNDER_MAINTENANCE) {
            throw new IllegalStateException("Véhicule non disponible.");
        }

        if (vehicle.getKm() > 150000 && vehicle.getStatus() == VehicleStatus.OUT_OF_SERVICE) {
            throw new IllegalStateException("Véhicule hors service : dépasse 150 000 km sans inspection.");
        }

        if (client.getHasUnpaidDebt() || client.getHasUnpaidCautions()) {
            throw new IllegalStateException("Client avec dette ou caution bloquée.");
        }

        Date startDate = rentalDtoIn.getStartDate();
        Date endDate = rentalDtoIn.getEndDate();

        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long duration = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

        if (duration < 1 || duration > 30) {
            throw new IllegalStateException("Durée de location invalide (1-30 jours).");
        }

        //Objectif: Savoir si ce client a loué ce véhicule au cours des 15 derniers jours
        LocalDate fifteenDaysAgo = LocalDate.now().minusDays(15);
        Date minStartDate = Date.from(fifteenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        boolean alreadyRented = rentalRepository.hasSameVehicleRentedByClientInLast15Days(
                client.getClientId(),
                vehicle.getVehicleId(),
                minStartDate
        );

        if (alreadyRented) {
            throw new IllegalStateException("Ce véhicule a déjà été loué par ce client dans les 15 derniers jours.");
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

    public List<RentalDtoOut> getRentalByClientId(Integer clientId) {
        List<Rental> rentals = rentalRepository.findRentalsByClient_ClientId(clientId);
        return rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RentalDtoOut> getRentalByClientIdAndStatus(Integer clientId, RentalStatus status) {
        List<Rental> rentals = rentalRepository.findRentalsByClient_ClientIdAndStatus(clientId, status);
        return rentals.stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }



}

