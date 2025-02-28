package com.auth.controller;

import com.auth.service.impl.PasswordServiceImpl;

public class PasswordController {
    private PasswordServiceImpl passwordService = new PasswordServiceImpl();

    // Handle reset Password request
    public void resetPassword(String usernameOrEmail, String newPassword) {
        try {
            passwordService.resetPassword(usernameOrEmail, newPassword);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}