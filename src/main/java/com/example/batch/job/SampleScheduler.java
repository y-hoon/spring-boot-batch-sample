package com.example.batch.job;

import lombok.Builder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
public class SampleScheduler {

    private final Job sampleJob;
    private final JobLauncher jobLauncher;
    private final Job simpleJob;

    @Builder
    public SampleScheduler(Job sampleJob, JobLauncher jobLauncher, Job simpleJob) {
        this.sampleJob = sampleJob;
        this.jobLauncher = jobLauncher;
        this.simpleJob = simpleJob;
    }

    //@Scheduled(fixedDelay = 15 * 1000L)
    @Scheduled(cron = "10 * * * * *")
    public void executeJob() {
        try {
            jobLauncher.run(
                    sampleJob,
                    new JobParametersBuilder().addString("requestDate", LocalDateTime.now().toString())
                    .toJobParameters()

            );
        } catch(JobExecutionException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Scheduled(cron = "20 * * * * *")
    public void executeJob2() {
        try {
            jobLauncher.run(
                    simpleJob,
                    new JobParametersBuilder().addString("datetime", LocalDateTime.now().toString())
                            .toJobParameters()

            );
        } catch(JobExecutionException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
