package com.cars.vehiclerentalsystem.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpcomingInspectionScheduler {

    private final JobLauncher jobLauncher;
    private final Job upcomingInspectionJob;

    public UpcomingInspectionScheduler(JobLauncher jobLauncher, Job upcomingInspectionJob) {
        this.jobLauncher = jobLauncher;
        this.upcomingInspectionJob = upcomingInspectionJob;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void runInspectionJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(upcomingInspectionJob, params);
    }
}