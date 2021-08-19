package com.levchugov.quoteprocessor.service.elvlstate;

import com.levchugov.quoteprocessor.exception.ElvlStateNotFoundException;
import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;

public class AskLessElvlState implements ElvlState {

    private final Elvl elvl;

    private final Quote quote;

    AskLessElvlState(Elvl elvl, Quote quote) {
        this.elvl = elvl;
        this.quote = quote;
    }

    @Override
    public ElvlState computeState() {
        if (quote.getAsk().compareTo(elvl.getValue()) < 0) {
            return this;
        } else {
            throw new ElvlStateNotFoundException("There is no state for elvl: " + elvl);
        }
    }

    @Override
    public Elvl computeElvl() {
        return elvl.toBuilder()
                .value(quote.getAsk())
                .build();
    }
}
