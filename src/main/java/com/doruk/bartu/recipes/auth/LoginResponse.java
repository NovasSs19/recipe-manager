package com.doruk.bartu.recipes.auth;

public class LoginResponse {
    public String message;
    public Long id;
    public String email;

    public LoginResponse(String message, Long id, String email) {
        this.message = message;
        this.id = id;
        this.email = email;
    }
}