package com.levchugov.quoteprocessor.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = BidValidator.class)
public @interface BidValidation {

    String message() default "Invalid Bid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
