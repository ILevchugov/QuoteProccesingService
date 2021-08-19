package com.levchugov.quoteprocessor.repository.impl;

import com.levchugov.quoteprocessor.model.Elvl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ActiveProfiles("test-containers")
class ElvlJdbcRepositoryTest {

    @Autowired
    private ElvlJdbcRepository repository;

    @Test
    public void test() {
        repository.save(
                Elvl.builder()
                        .value(BigDecimal.TEN)
                        .isin("ISIN")
                        .build()
        );

        Elvl elvl = repository.findByIsin("ISIN").orElse(null);

        Assertions.assertNotNull(elvl);
        Assertions.assertEquals(BigDecimal.TEN, elvl.getValue());
        Assertions.assertEquals("ISIN", elvl.getIsin());
    }

    @Test
    public void testWithId() {
        final String isin = "ISIN111";

        repository.save(
                Elvl.builder()
                        .value(BigDecimal.TEN)
                        .isin(isin)
                        .build()
        );

        Elvl elvl = repository.findByIsin(isin).orElse(Elvl.builder().build());
        elvl = elvl.toBuilder()
                .value(BigDecimal.ONE)
                .build();

        repository.save(elvl);

        elvl = repository.findByIsin(isin).orElse(Elvl.builder().build());

        Assertions.assertEquals(
                Elvl.builder()
                        .id(elvl.getId())
                        .value(BigDecimal.ONE)
                        .isin(isin)
                        .build(),
                elvl
        );
    }

    @Test
    public void testFindAll() {
        repository.save(
                Elvl.builder()
                        .value(BigDecimal.TEN)
                        .isin("ISIN2")
                        .build()
        );

        repository.save(
                Elvl.builder()
                        .value(BigDecimal.TEN)
                        .isin("ISIN3")
                        .build()
        );

        List<Elvl> elvlList = repository.findAll(2, 0);

        Assertions.assertEquals(2, elvlList.size());
    }

    @Test
    public void testFindByIsinEmpty() {
        Elvl elvl = repository.findByIsin("kek").orElse(null);
        Assertions.assertNull(elvl);
    }

}