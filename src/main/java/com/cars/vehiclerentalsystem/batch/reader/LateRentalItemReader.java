package com.cars.vehiclerentalsystem.batch.reader;

import com.cars.vehiclerentalsystem.entity.Rental;
import com.cars.vehiclerentalsystem.enums.RentalStatus;
import com.cars.vehiclerentalsystem.repository.RentalRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class LateRentalItemReader implements ItemReader<Rental> {

    private final RentalRepository rentalRepository;
    private List<Rental> lateRentals;
    private int nextIndex = 0;

    public LateRentalItemReader(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental read() {
        if (lateRentals == null) {
            LocalDate overdueThreshold = LocalDate.now().minusDays(3);
            Date cutoffDate = Date.from(overdueThreshold.atStartOfDay(ZoneId.systemDefault()).toInstant());

            lateRentals = rentalRepository.findByStatusAndEndDateBefore(RentalStatus.ACTIVE, cutoffDate);
        }

        if (nextIndex < lateRentals.size()) {
            return lateRentals.get(nextIndex++);
        } else {
            return null;
        }
    }
}
