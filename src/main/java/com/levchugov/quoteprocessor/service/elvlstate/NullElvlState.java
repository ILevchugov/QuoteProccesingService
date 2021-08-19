package com.levchugov.quoteprocessor.service.elvlstate;

import com.levchugov.quoteprocessor.model.Elvl;
import com.levchugov.quoteprocessor.model.Quote;

public class NullElvlState implements ElvlState {

    private final ElvlState emptyBidState;

    private final Elvl elvl;

    private final Quote quote;

    public NullElvlState(Elvl elvl, Quote quote) {
        this.elvl = elvl;
        this.quote = quote;
        this.emptyBidState = new EmptyBidElvlState(elvl, quote);
    }

    @Override
    public ElvlState computeState() {
        if (elvl == null) {
            return this;
        } else {
            return emptyBidState.computeState();
        }
    }

    @Override
    public Elvl computeElvl() {
        return Elvl.builder()
                .value(quote.getBid() != null ? quote.getBid() : quote.getAsk())
                .isin(quote.getIsin())
                .build();
    }
}
