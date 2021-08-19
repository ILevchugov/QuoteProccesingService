package com.levchugov.quoteprocessor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Elvl.ElvlBuilder.class)
public class Elvl {

    Long id;

    String isin;

    BigDecimal value;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ElvlBuilder {
    }

}
