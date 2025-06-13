package com.cars.vehiclerentalsystem.batch.processor;

import com.cars.vehiclerentalsystem.entity.Vehicle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UpcomingInspectionItemProcessor implements ItemProcessor<Vehicle, String> {

    @Override
    public String process(Vehicle vehicle) {
        return "Vehicle " + vehicle.getVehicleId() + ") needs inspection within 7 days.";
    }
}