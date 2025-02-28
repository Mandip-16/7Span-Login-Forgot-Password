package com.auth.service.impl;

import com.auth.model.User;
import com.auth.service.PasswordService;
import static com.auth.utility.InputUtil.isValidPassword;

public class PasswordServiceImpl implements PasswordService {
    private AuthServiceImpl authService = new AuthServiceImpl();

    //Reset Password functionality
    @Override
    public void resetPassword(String usernameOrEmail, String newPassword) {

        if (!isValidPassword(newPassword)) {
            System.out.println("Password does not meet the requirements.");
            System.out.println("Password Requirement:\n"+"At least 8 characters\n" +
                    "At least 1 uppercase letter\n" +
                    "At least 1 lowercase letter\n" +
                    "At least 1 number\n" +
                    "At least 1 underscore");
            return;
        }

        User user = authService.findUser(usernameOrEmail);
        user.setPassword(newPassword); // Update new password
        user.setBlocked(false); // User will unblock

        System.out.println("Password reset successfully for user: " + usernameOrEmail);
    }
}