package com.auth.service.impl;

import com.auth.model.User;
import com.auth.service.PasswordService;
import static com.auth.util.InputUtil.isValidPassword;

public class PasswordServiceImpl implements PasswordService {
    private AuthServiceImpl authService = new AuthServiceImpl();

    //Reset Password functionality
    @Override
    public void resetPassword(String usernameOrEmail, String newPassword, String confirmPassword) {

        if (!isValidPassword(newPassword)) {
            System.out.println("Password does not meet the requirements.");
            return;
        }

        User user = authService.findUser(usernameOrEmail);
        user.setPassword(newPassword);
        user.setBlocked(false);

        System.out.println("Password reset successfully for user: " + usernameOrEmail);
    }
}