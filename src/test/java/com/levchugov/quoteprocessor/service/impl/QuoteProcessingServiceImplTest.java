package com.levchugov.quoteprocessor.service.impl;

import com.levchugov.quoteprocessor.exception.ElvlStateNotFoundException;
import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;
import com.levchugov.quoteprocessor.repository.ElvlRepository;
import com.levchugov.quoteprocessor.repository.QuoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class QuoteProcessingServiceImplTest {

    private static final String ISIN = "RU000A0JX0J2";

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private ElvlRepository elvlRepository;

    @InjectMocks
    private QuoteProcessingServiceImpl quoteProcessingService;

    @Test
    public void testProcessQuoteNewElvlShouldSaveBid() {
        Quote quote = Quote.builder()
                .isin(ISIN)
                .bid(BigDecimal.valueOf(100.2))
                .ask(BigDecimal.valueOf(101.9))
                .build();

        Mockito.doNothing().when(quoteRepository).save(quote);
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(Optional.empty());

        quoteProcessingService.processQuote(
                Quote.builder()
                        .isin(ISIN)
                        .bid(BigDecimal.valueOf(100.2))
                        .ask(BigDecimal.valueOf(101.9))
                        .build()
        );

        Mockito.verify(elvlRepository).save(Elvl.builder()
                .value(BigDecimal.valueOf(100.2))
                .isin(ISIN)
                .build());
    }

    @Test
    public void testProcessQuoteBidMoreThenElvl() {
        Quote quote = Quote.builder()
                .isin(ISIN)
                .bid(BigDecimal.valueOf(100.5))
                .ask(BigDecimal.valueOf(101.9))
                .build();

        Mockito.doNothing().when(quoteRepository).save(quote);
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(
                Optional.ofNullable(
                        Elvl.builder()
                                .isin(ISIN)
                                .value(BigDecimal.valueOf(100.1))
                                .build()
                )
        );

        quoteProcessingService.processQuote(quote);

        Mockito.verify(elvlRepository).save(
                Elvl.builder()
                        .value(BigDecimal.valueOf(100.5))
                        .isin(ISIN)
                        .build()
        );
    }

    @Test
    public void testProcessQuoteAskLessThenElvl() {
        Quote quote = Quote.builder()
                .isin(ISIN)
                .bid(BigDecimal.valueOf(200.5))
                .ask(BigDecimal.valueOf(201.9))
                .build();

        Mockito.doNothing().when(quoteRepository).save(quote);
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(
                Optional.ofNullable(
                        Elvl.builder()
                                .isin(ISIN)
                                .value(BigDecimal.valueOf(202))
                                .build()
                )
        );

        quoteProcessingService.processQuote(quote);

        Mockito.verify(elvlRepository).save(
                Elvl.builder()
                        .value(BigDecimal.valueOf(201.9))
                        .isin(ISIN)
                        .build()
        );
    }

    @Test
    public void testProcessQuoteAskBidNull() {
        Quote quote = Quote.builder()
                .isin(ISIN)
                .bid(null)
                .ask(BigDecimal.valueOf(201.9))
                .build();

        Mockito.doNothing().when(quoteRepository).save(quote);
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(
                Optional.ofNullable(
                        Elvl.builder()
                                .isin(ISIN)
                                .value(BigDecimal.valueOf(200))
                                .build()
                )
        );

        quoteProcessingService.processQuote(quote);

        Mockito.verify(elvlRepository).save(
                Elvl.builder()
                        .value(BigDecimal.valueOf(201.9))
                        .isin(ISIN)
                        .build()
        );
    }

    @Test
    public void testProcessQuoteNoState() {
        Quote quote = Quote.builder()
                .isin(ISIN)
                .bid(BigDecimal.valueOf(180))
                .ask(BigDecimal.valueOf(200))
                .build();

        Mockito.doNothing().when(quoteRepository).save(quote);
        Mockito.when(elvlRepository.findByIsin(ISIN)).thenReturn(
                Optional.ofNullable(
                        Elvl.builder()
                                .id(1L)
                                .isin(ISIN)
                                .value(BigDecimal.valueOf(200))
                                .build()
                )
        );

        Assertions.assertThrows(
                ElvlStateNotFoundException.class,
                () -> quoteProcessingService.processQuote(quote)
        );

    }

}