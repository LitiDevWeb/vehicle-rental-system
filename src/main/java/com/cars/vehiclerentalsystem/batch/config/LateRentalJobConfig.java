package com.cars.vehiclerentalsystem.batch.config;

import com.cars.vehiclerentalsystem.batch.processor.LateRentalItemProcessor;
import com.cars.vehiclerentalsystem.batch.reader.LateRentalItemReader;
import com.cars.vehiclerentalsystem.batch.writer.LateRentalItemWriter;
import com.cars.vehiclerentalsystem.entity.Rental;
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
public class LateRentalJobConfig {

    @Bean
    public Step checkLateRentalsStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     LateRentalItemReader reader,
                                     LateRentalItemProcessor processor,
                                     LateRentalItemWriter writer) {

        return new StepBuilder("checkLateRentalsStep", jobRepository)
                .<Rental, Rental>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job checkLateRentalsJob(JobRepository jobRepository,
                                   Step checkLateRentalsStep) {

        return new JobBuilder("checkLateRentalsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkLateRentalsStep)
                .build();
    }
}
