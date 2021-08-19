package com.levchugov.quoteprocessor.repository;

import com.levchugov.quoteprocessor.model.Quote;

public interface QuoteRepository {

    void save(Quote quote);

}
