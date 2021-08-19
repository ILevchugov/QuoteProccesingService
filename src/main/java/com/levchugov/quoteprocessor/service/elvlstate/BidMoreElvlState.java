package com.levchugov.quoteprocessor.service.elvlstate;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;

public class BidMoreElvlState implements ElvlState {

    private final ElvlState askLessState;

    private final Elvl elvl;

    private final Quote quote;

    BidMoreElvlState(Elvl elvl, Quote quote) {
        this.elvl = elvl;
        this.quote = quote;
        this.askLessState = new AskLessElvlState(elvl, quote);
    }

    @Override
    public ElvlState computeState() {
        if (quote.getBid().compareTo(elvl.getValue()) > 0) {
            return this;
        } else {
            return askLessState.computeState();
        }
    }

    @Override
    public Elvl computeElvl() {
        return elvl.toBuilder()
                .value(quote.getBid())
                .build();
    }
}
