package com.auth.controller;

import com.auth.exception.UserNotFoundException;
import com.auth.model.User;
import com.auth.service.impl.AuthServiceImpl;

import java.util.Scanner;

public class ForgotPasswordController {
    private OTPController otpController;
    private PasswordController passwordController;

    public ForgotPasswordController(OTPController otpController, PasswordController passwordController) {
        this.otpController = otpController;
        this.passwordController = passwordController;
    }

    // Handles the forgot password flow
    public void forgotPasswordFlow(Scanner scanner) {
        System.out.println("\n--- Forgot Password ---");
        System.out.println("Enter username or email: ");
        String usernameOrEmail = scanner.nextLine();

        AuthServiceImpl authService = new AuthServiceImpl();
        User user;
        try{
            user = authService.findUser(usernameOrEmail);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        // This condition is for blocked user from login so user can perform forgot password
        if(user.getLoginAttempts() < 3 || user.getOtpAttempts() >= 3) {
            if (user.isBlocked()) {
                System.out.println("Account is now blocked due to 3 invalid attempts.");
                return;
            }
        }

        // Handling send OTP
        otpController.sendOTP(usernameOrEmail);

        boolean otpVerified = false;

        // Allow 3 OTP attempts
        for (int i = 1; i <= 3; i++) {
            System.out.println("Enter OTP (attempt " + i + "): ");
            String otp = scanner.nextLine();

            try {
                otpController.verifyOTP(usernameOrEmail, otp);
                otpVerified = true;
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (!otpVerified) {
            user.setBlocked(true);
            System.out.println("Too many failed OTP attempts. Account is blocked.");
            return;
        }

        String newPassword;
        String confirmPassword;

        do {
            System.out.println("\nEnter New Password:");
            System.out.println("New Password: ");
            newPassword = scanner.nextLine();
            System.out.println("Confirm Password: ");
            confirmPassword = scanner.nextLine();
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            }
        } while (!newPassword.equals(confirmPassword));

            passwordController.resetPassword(usernameOrEmail, newPassword);
    }
}