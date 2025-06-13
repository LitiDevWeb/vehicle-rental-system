package com.cars.vehiclerentalsystem.batch.processor;

import com.cars.vehiclerentalsystem.entity.Rental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LateRentalItemProcessor implements ItemProcessor<Rental, Rental> {

    @Override
    public Rental process(Rental rental) {
        log.info("Traitement de la location ID: " + rental.getRentalId());
        return rental;
    }
}