package com.levchugov.quoteprocessor.validation;

import com.levchugov.quoteprocessor.model.Quote;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class BidValidator implements ConstraintValidator<BidValidation, Quote> {
    @Override
    public void initialize(BidValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Quote value, ConstraintValidatorContext context) {
        return value.getBid() == null || value.getBid().compareTo(value.getAsk()) < 0;
    }
}
