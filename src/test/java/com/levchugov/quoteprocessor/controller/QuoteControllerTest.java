package com.levchugov.quoteprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levchugov.quoteprocessor.model.Quote;
import com.levchugov.quoteprocessor.service.QuoteProccesingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = QuoteController.class)
@Import({QuoteController.class})
class QuoteControllerTest {

    private static final String ISIN = "RU000A0JX0J2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuoteProccesingService quoteProccesingService;

    @Test
    public void testSuccesfullPostQuote() throws Exception {
        Mockito.doNothing().when(quoteProccesingService)
                .processQuote(Quote.builder()
                        .isin(ISIN)
                        .ask(BigDecimal.TEN)
                        .bid(BigDecimal.ONE)
                        .build());

        String sourceJson = objectMapper.writeValueAsString(
                Quote.builder()
                        .isin(ISIN)
                        .ask(BigDecimal.TEN)
                        .bid(BigDecimal.ONE)
                        .build()
        );

        mockMvc
                .perform(MockMvcRequestBuilders.post("/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testFailedPostQuoteBidMoreThenAsk() throws Exception {
        Mockito.doNothing().when(quoteProccesingService)
                .processQuote(Quote.builder()
                        .isin(ISIN)
                        .ask(BigDecimal.ONE)
                        .bid(BigDecimal.TEN)
                        .build());

        String sourceJson = objectMapper.writeValueAsString(
                Quote.builder()
                        .isin(ISIN)
                        .ask(BigDecimal.ONE)
                        .bid(BigDecimal.TEN)
                        .build()
        );

        mockMvc
                .perform(MockMvcRequestBuilders.post("/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFailedPostQuoteIsin13() throws Exception {
        Mockito.doNothing().when(quoteProccesingService)
                .processQuote(Quote.builder()
                        .isin(ISIN+"1")
                        .ask(BigDecimal.ONE)
                        .bid(BigDecimal.TEN)
                        .build());

        String sourceJson = objectMapper.writeValueAsString(
                Quote.builder()
                        .isin(ISIN)
                        .ask(BigDecimal.ONE)
                        .bid(BigDecimal.TEN)
                        .build()
        );

        mockMvc
                .perform(MockMvcRequestBuilders.post("/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isBadRequest());
    }

}