package com.levchugov.quoteprocessor.controller;

import com.levchugov.quoteprocessor.model.Quote;
import com.levchugov.quoteprocessor.service.QuoteProccesingService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class QuoteController {

    private final QuoteProccesingService quoteProcessingService;

    @PostMapping("/quotes")
    @Async("threadPoolTaskExecutor")
    public void addQuote(@RequestBody @Valid Quote quote) {
        quoteProcessingService.processQuote(quote);
    }

}
