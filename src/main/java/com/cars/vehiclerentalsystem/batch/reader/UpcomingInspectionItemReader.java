package com.cars.vehiclerentalsystem.batch.reader;

import com.cars.vehiclerentalsystem.entity.Vehicle;
import com.cars.vehiclerentalsystem.repository.InspectionRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class UpcomingInspectionItemReader implements ItemReader<Vehicle> {

    private final InspectionRepository inspectionRepository;
    private List<Vehicle> vehiclesToInspect;
    private int nextIndex = 0;

    public UpcomingInspectionItemReader(InspectionRepository inspectionRepository) {
        this.inspectionRepository = inspectionRepository;
    }

    @Override
    public Vehicle read() {
        if (vehiclesToInspect == null) {
            LocalDate threshold = LocalDate.now().minusDays(83); // 90 - 7 jours
            Date date = Date.from(threshold.atStartOfDay(ZoneId.systemDefault()).toInstant());

            vehiclesToInspect = inspectionRepository.findVehiclesToInspectBefore(date);
        }

        return (nextIndex < vehiclesToInspect.size()) ? vehiclesToInspect.get(nextIndex++) : null;
    }
}