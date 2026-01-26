package com.doruk.bartu.recipes.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.doruk.bartu.recipes.validation.NoAdmin;

public class RegisterRequest {

    @Email
    @NotBlank
    public String email;

    @NotBlank
    @Size(min = 6, max = 72)
    @NoAdmin
    public String password;
}
