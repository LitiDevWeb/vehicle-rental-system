package com.cars.vehiclerentalsystem.batch.writer;

import com.cars.vehiclerentalsystem.entity.Rental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LateRentalItemWriter implements ItemWriter<Rental> {

    @Override
    public void write(Chunk<? extends Rental> chunk) {
        for (Rental rental : chunk) {
            log.info("Location en retard - ID: {}, Client: {}, Fin: {}",
                    rental.getRentalId(),
                    rental.getClient().getName(),
                    rental.getEndDate());
        }
    }
}
