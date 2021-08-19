package com.levchugov.quoteprocessor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.levchugov.quoteprocessor.validation.BidValidation;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@BidValidation
@JsonDeserialize(builder = Quote.QuoteBuilder.class)
public class Quote {

    Long id;

    @Size(min = 12, max = 12)
    String isin;

    BigDecimal bid;

    BigDecimal ask;

    @JsonPOJOBuilder(withPrefix = "")
    public static class QuoteBuilder {
    }

}
