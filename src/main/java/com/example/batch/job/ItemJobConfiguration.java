package com.example.batch.job;

import com.example.batch.QueueItemReader;
import com.example.batch.User;
import com.example.batch.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Configuration
public class ItemJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final UserService userService;

    @Builder
    public ItemJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, UserService userService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;

        this.userService = userService;
    }

    @Bean
    public Job userStatusJob() {
        return jobBuilderFactory.get("userStatusJob")
                .start(userStatusStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step userStatusStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("userStatusStep")
                .<User, User>chunk(10)
                .reader(userStatusReader(requestDate))
                .processor(userStatusProcessor())
                .writer(userStatusWriter())
                .build();
    }

    @Bean
    @StepScope
    public QueueItemReader<User> userStatusReader(@Value("#{jobParameters[requestDate]}") String requestDate) {

        log.info("====== reader ing requestDate : {}", requestDate);

        LocalDateTime checkDate = LocalDateTime.parse(requestDate).minusMonths(1);

        List<User> users = userService.findUsersByCreateDate(checkDate);

        return new QueueItemReader<>(users);
    }

    public ItemProcessor<User, User> userStatusProcessor() {

        return new ItemProcessor<User, User>() {

            @Override
            public User process(User item) throws Exception {

                log.info("====== process ing");


                item.setPhone();
                return item;
            }
        };
    }

    public ItemWriter<User> userStatusWriter() {

        log.info("====== writer ing");

        return ((List<? extends User> users) -> userService.saveAll((List<User>) users));
    }


}
