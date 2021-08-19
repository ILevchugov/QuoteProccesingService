package com.levchugov.quoteprocessor.repository.impl;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.repository.ElvlRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ElvlJdbcRepository implements ElvlRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Elvl> findAll(Integer limit, Integer offset) {
        String selectQuery = "select * from elvls order by id limit ? offset ?";
        return jdbcTemplate.query(
                selectQuery,
                new Object[]{limit, offset},
                (rs, rowNum) -> Elvl.builder()
                        .id(rs.getLong("id"))
                        .isin(rs.getString("isin"))
                        .value(rs.getBigDecimal("elvl_value"))
                        .build()
        );

    }

    @Override
    public Optional<Elvl> findByIsin(String isin) {
        String selectQuery = "select  * from elvls where isin = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            selectQuery,
                            new Object[]{isin},
                            (rs, rowNum) ->
                                    Elvl.builder()
                                            .id(rs.getLong("id"))
                                            .isin(rs.getString("isin"))
                                            .value(rs.getBigDecimal("elvl_value"))
                                            .build()

                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Elvl elvl) {
        String updateQuery = "update elvls set isin = ?, elvl_value = ?  where id = ?";
        String saveQuery = "insert into elvls (isin, elvl_value) values (?, ?)";

        if (elvl.getId() != null) {
            jdbcTemplate.update(
                    updateQuery,
                    elvl.getIsin(),
                    elvl.getValue(),
                    elvl.getId()
            );
        } else {
            jdbcTemplate.update(
                    saveQuery,
                    elvl.getIsin(),
                    elvl.getValue()
            );
        }
    }
}
