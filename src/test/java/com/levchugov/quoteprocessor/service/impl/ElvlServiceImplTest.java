package com.levchugov.quoteprocessor.service.impl;

import com.levchugov.quoteprocessor.exception.ElvlNotFoundException;
import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.repository.ElvlRepository;
import com.levchugov.quoteprocessor.service.ElvlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ElvlServiceImplTest {

    private static final String ISIN = "RU000A0JX0J2";

    @Mock
    private ElvlRepository elvlRepository;

    @InjectMocks
    private ElvlServiceImpl elvlService;

    @Test
    public void testGetAll() {
        List<Elvl> elvls = Collections.singletonList(
                Elvl.builder()
                        .isin(ISIN)
                        .value(BigDecimal.TEN)
                        .build()
        );

        Mockito.when(elvlRepository.findAll(1, 0)).thenReturn(elvls);

        List<Elvl> actualElvls = elvlService.getAll(1, 0);

        Assertions.assertEquals(elvls, actualElvls);

        Mockito.verify(elvlRepository, Mockito.times(1)).findAll(1, 0);
    }

    @Test
    public void testGetByIsin() {
        Elvl expectedElvl = Elvl.builder()
                .isin(ISIN)
                .value(BigDecimal.TEN)
                .build();

        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(Optional.ofNullable(expectedElvl));

        Elvl actualElvl = elvlService.getByIsin(ISIN);

        Mockito.verify(elvlRepository, Mockito.times(1)).findByIsin(ISIN);
        assertEquals(expectedElvl, actualElvl);
    }

    @Test
    public void testGetByIsinNotFound() {
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                ElvlNotFoundException.class,
                () -> elvlService.getByIsin(ISIN)
        );
    }

}