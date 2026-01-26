package com.doruk.bartu.recipes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoAdminValidator implements ConstraintValidator<NoAdmin, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.toLowerCase().contains("admin");
    }
}