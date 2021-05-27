package com.example.batch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);    //기본적으로 실행 대기하는 Thread 갯수
        executor.setMaxPoolSize(10);    //동시 동작하는 최대 Thread 갯수
        executor.setQueueCapacity(100);     //최대 Thread를 초과하게 되면 새로운 요청을 Queue에 저장하고 실행가능시 실행함
        executor.setThreadNamePrefix("hdmedi-async-");  //Thread 의 접두사
        executor.initialize();
        return executor;

    }

}
