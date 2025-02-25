package com.auth.service.impl;

import com.auth.exception.InvalidPasswordException;
import com.auth.model.User;
import com.auth.service.PasswordService;

import static com.auth.util.InputUtil.isValidPassword;

public class PasswordServiceImpl implements PasswordService {
    private AuthServiceImpl authService = new AuthServiceImpl();

    @Override
    public void resetPassword(String usernameOrEmail, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidPasswordException("Passwords do not match.");
        }

        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("Password does not meet the requirements.");
        }

        User user = authService.findUser(usernameOrEmail);
        user.setPassword(newPassword);
        user.setBlocked(false);

        System.out.println("Password reset successfully for user: " + usernameOrEmail);
    }
}