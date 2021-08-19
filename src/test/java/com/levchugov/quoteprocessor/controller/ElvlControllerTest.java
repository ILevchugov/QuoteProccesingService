package com.levchugov.quoteprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levchugov.quoteprocessor.exception.ElvlNotFoundException;
import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.service.ElvlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ElvlController.class)
@Import({ElvlController.class})
class ElvlControllerTest {

    private static final String ISIN = "RU000A0JX0J2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ElvlService elvlService;

    @Test
    public void testGetElvlByIsin() throws Exception {
        final Elvl elvl = Elvl.builder()
                .isin(ISIN)
                .value(BigDecimal.TEN)
                .build();

        Mockito.when(elvlService.getByIsin(ISIN)).thenReturn(elvl);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/elvls/{isin}", ISIN))
                .andExpect(content().string(objectMapper.writeValueAsString(elvl)))
                .andExpect(status().isOk());
    }


    public void testFailed() throws Exception {
        Mockito.when(elvlService.getByIsin(ISIN)).thenThrow(new ElvlNotFoundException("not found"));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/elvls/{isin}", ISIN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllElvls() throws Exception {
        final Elvl elvl = Elvl.builder()
                .isin(ISIN)
                .value(BigDecimal.TEN)
                .build();

        Mockito.when(elvlService.getAll(1, 0)).thenReturn(Collections.singletonList(elvl));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/elvls?limit={limit}&offset={offset}", 1, 0))
                .andExpect(content().string(objectMapper.writeValueAsString(Collections.singletonList(elvl))))
                .andExpect(status().isOk());
    }

}