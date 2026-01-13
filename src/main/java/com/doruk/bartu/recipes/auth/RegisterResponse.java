package com.doruk.bartu.recipes.auth;

public class RegisterResponse {
    public Long id;
    public String email;

    public RegisterResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
