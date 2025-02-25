package com.auth.controller;

import com.auth.service.impl.PasswordServiceImpl;

public class PasswordController {
    private PasswordServiceImpl passwordService = new PasswordServiceImpl();

    public void resetPassword(String usernameOrEmail, String newPassword, String confirmPassword) {
        try {
            passwordService.resetPassword(usernameOrEmail, newPassword, confirmPassword);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}