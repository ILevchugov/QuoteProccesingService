package com.levchugov.quoteprocessor.repository;

import com.levchugov.quoteprocessor.model.Elvl;

import java.util.List;
import java.util.Optional;

public interface ElvlRepository {

    List<Elvl> findAll(Integer limit, Integer offset);

    Optional<Elvl> findByIsin(String isin);

    void save(Elvl elvl);

}
