package com.cars.vehiclerentalsystem.batch.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpcomingInspectionItemWriter implements ItemWriter<String> {

    @Override
    public void write(Chunk<? extends String> items) {
        items.forEach(System.out::println);
    }
}
