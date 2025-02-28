package com.auth.service;

public interface PasswordService {
    void resetPassword(String usernameOrEmail, String newPassword);
}