package com.jhy.studentrecord.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {
    String regexp();
    String message() default "Enum value is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
