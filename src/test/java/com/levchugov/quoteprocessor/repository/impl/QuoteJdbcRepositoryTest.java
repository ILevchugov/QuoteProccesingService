package com.levchugov.quoteprocessor.repository.impl;

import com.levchugov.quoteprocessor.model.Quote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ActiveProfiles("test-containers")
class QuoteJdbcRepositoryTest {

    @Autowired
    private QuoteJdbcRepository repository;

    @Test
    void test() {
        repository.save(
                Quote.builder()
                        .isin("ISIN")
                        .ask(BigDecimal.ONE)
                        .bid(BigDecimal.ONE)
                        .build()
        );
    }

}