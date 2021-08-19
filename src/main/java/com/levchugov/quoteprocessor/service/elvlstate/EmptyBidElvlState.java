package com.levchugov.quoteprocessor.service.elvlstate;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;

public class EmptyBidElvlState implements ElvlState {

    private final ElvlState bidMoreState;

    private final Elvl elvl;

    private final Quote quote;

    EmptyBidElvlState(Elvl elvl, Quote quote) {
        this.elvl = elvl;
        this.quote = quote;
        this.bidMoreState = new BidMoreElvlState(elvl, quote);
    }

    @Override
    public ElvlState computeState() {
        if (quote.getBid() == null) {
            return this;
        } else {
            return bidMoreState.computeState();
        }
    }

    @Override
    public Elvl computeElvl() {
        return elvl.toBuilder()
                .value(quote.getAsk())
                .build();
    }
}
