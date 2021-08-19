package com.levchugov.quoteprocessor.service.impl;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;
import com.levchugov.quoteprocessor.repository.ElvlRepository;
import com.levchugov.quoteprocessor.repository.QuoteRepository;
import com.levchugov.quoteprocessor.service.QuoteProccesingService;
import com.levchugov.quoteprocessor.service.elvlstate.NullElvlState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class QuoteProcessingServiceImpl implements QuoteProccesingService {

    private final QuoteRepository quoteRepository;

    private final ElvlRepository elvlRepository;

    @Override
    @Transactional
    public void processQuote(Quote quote) {
        quoteRepository.save(quote);
        Elvl elvl = elvlRepository.findByIsin(quote.getIsin()).orElse(null);

        log.info("Elvl {}", elvl);

        Elvl computedElvl = new NullElvlState(elvl, quote)
                .computeState()
                .computeElvl();

        log.info("computedElvl {}", computedElvl);

        elvlRepository.save(computedElvl);
    }

}
