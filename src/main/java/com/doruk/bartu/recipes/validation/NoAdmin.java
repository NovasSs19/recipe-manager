package com.doruk.bartu.recipes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoAdminValidator.class)
public @interface NoAdmin {
    String message() default "Password cannot contain the word 'admin'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}