package com.cars.vehiclerentalsystem.service;

import com.cars.vehiclerentalsystem.dto.ClientDtoIn;
import com.cars.vehiclerentalsystem.dto.ClientDtoOut;
import com.cars.vehiclerentalsystem.entity.Client;
import com.cars.vehiclerentalsystem.mapper.ClientMapper;
import com.cars.vehiclerentalsystem.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper){
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDtoOut addClient(ClientDtoIn clientDtoIn){
        Client client = clientMapper.toEntity(clientDtoIn);

        client.setCreatedAt(new Date());
        client.setHasUnpaidDebt(false);
        client.setHasUnpaidCautions(false);

        Client saveClient = clientRepository.save(client);
        return clientMapper.toDto(saveClient);
    }


}
