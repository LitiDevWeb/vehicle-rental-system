package com.cars.vehiclerentalsystem.batch.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
    public class LateRentalScheduler {

        private final JobLauncher jobLauncher;
        private final Job checkLateRentalsJob;

        public LateRentalScheduler(JobLauncher jobLauncher, Job checkLateRentalsJob) {
            this.jobLauncher = jobLauncher;
            this.checkLateRentalsJob = checkLateRentalsJob;
        }

        @Scheduled(cron = "0 0 2 * * *")
        public void runLateRentalJob() {
            try {
                log.info("Début exécution du job 'checkLateRentalsJob' à {}", LocalDateTime.now());

                jobLauncher.run(
                        checkLateRentalsJob,
                        new JobParametersBuilder()
                                .addLong("startAt", System.currentTimeMillis())
                                .toJobParameters()
                );

                log.info("Job 'checkLateRentalsJob' exécuté avec succès");
            } catch (Exception e) {
                log.error("Erreur lors de l'exécution du job 'checkLateRentalsJob'", e);
            }
        }
    }

