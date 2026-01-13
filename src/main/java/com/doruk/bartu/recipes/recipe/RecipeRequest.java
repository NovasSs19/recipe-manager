package com.doruk.bartu.recipes.recipe;

import jakarta.validation.constraints.NotBlank;

public record RecipeRequest(
        @NotBlank String title,
        String description
) {
}