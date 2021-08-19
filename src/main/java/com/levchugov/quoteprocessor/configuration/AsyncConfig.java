package com.levchugov.quoteprocessor.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@AllArgsConstructor
public class AsyncConfig {

    private final ThreadPoolProperties poolProperties;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolProperties.getCorePoolSize());
        executor.setMaxPoolSize(poolProperties.getMaxPoolSize());
        executor.setQueueCapacity(poolProperties.getQueueCapacity());
        executor.setThreadNamePrefix("QuoteProcessor-");
        executor.initialize();
        return executor;
    }

}
