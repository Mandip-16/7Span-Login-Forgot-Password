package com.auth.controller;

import com.auth.service.impl.AuthServiceImpl;

public class AuthController {
    private AuthServiceImpl authService = new AuthServiceImpl();

    // Handles the login request
    public void login(String usernameOrEmail, String password) {
        try {
            authService.login(usernameOrEmail, password);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}