package com.cars.vehiclerentalsystem.batch.config;

import com.cars.vehiclerentalsystem.batch.processor.UpcomingInspectionItemProcessor;
import com.cars.vehiclerentalsystem.batch.reader.UpcomingInspectionItemReader;
import com.cars.vehiclerentalsystem.batch.writer.UpcomingInspectionItemWriter;
import com.cars.vehiclerentalsystem.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UpcomingInspectionJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final UpcomingInspectionItemReader reader;
    private final UpcomingInspectionItemProcessor processor;
    private final UpcomingInspectionItemWriter writer;

    @Bean
    public Step upcomingInspectionStep() {
        return new StepBuilder("upcomingInspectionStep", jobRepository)
                .<Vehicle, String>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job upcomingInspectionJob() {
        return new JobBuilder("upcomingInspectionJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(upcomingInspectionStep())
                .build();
    }
}

