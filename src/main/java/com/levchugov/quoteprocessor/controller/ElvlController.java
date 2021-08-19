package com.levchugov.quoteprocessor.controller;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.service.ElvlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ElvlController {

    private final ElvlService elvlService;

    @GetMapping("/elvls/{isin}")
    public Elvl getElvlByIsis(@PathVariable String isin) {
        return elvlService.getByIsin(isin);
    }

    @GetMapping("/elvls")
    public List<Elvl> getAllElvls(
            @RequestParam Integer limit,
            @RequestParam Integer offset
    ) {
        return elvlService.getAll(limit, offset);
    }

}
