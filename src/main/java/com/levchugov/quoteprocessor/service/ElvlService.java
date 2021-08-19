package com.levchugov.quoteprocessor.service;

import com.levchugov.quoteprocessor.model.Elvl;

import java.util.List;

public interface ElvlService {

    Elvl getByIsin(String isin);

    List<Elvl> getAll(Integer limit, Integer offset);

}
