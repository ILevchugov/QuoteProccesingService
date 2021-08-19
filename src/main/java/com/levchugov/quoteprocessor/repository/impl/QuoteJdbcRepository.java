package com.levchugov.quoteprocessor.repository.impl;

import com.levchugov.quoteprocessor.model.Quote;
import com.levchugov.quoteprocessor.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class QuoteJdbcRepository implements QuoteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Quote quote) {
        String saveQuery = "insert into quotes (isin, bid, ask, date) values (?, ?, ?, ?)";

        jdbcTemplate.update(
                saveQuery,
                quote.getIsin(),
                quote.getBid(),
                quote.getAsk(),
                Timestamp.valueOf(LocalDateTime.now())
        );
    }
}
