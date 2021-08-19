package com.levchugov.quoteprocessor.service.impl;

import com.levchugov.quoteprocessor.exception.ElvlNotFoundException;
import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.repository.ElvlRepository;
import com.levchugov.quoteprocessor.service.ElvlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElvlServiceImpl implements ElvlService {

    private final ElvlRepository elvlRepository;

    @Override
    public Elvl getByIsin(String isin) {
        return elvlRepository.findByIsin(isin).orElseThrow(
                () -> new ElvlNotFoundException("Elvl by this isin not found")
        );
    }

    @Override
    public List<Elvl> getAll(Integer limit, Integer offset) {
        return elvlRepository.findAll(limit, offset);
    }
}
