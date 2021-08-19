package com.levchugov.quoteprocessor;

import com.levchugov.quoteprocessor.configuration.ThreadPoolProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class QuoteProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuoteProcessorApplication.class, args);
    }
}
