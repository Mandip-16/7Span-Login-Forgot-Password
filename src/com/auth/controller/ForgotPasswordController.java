package com.auth.controller;

import com.auth.exception.TooManyFailedAttemptsException;
import com.auth.exception.UserNotFoundException;
import com.auth.model.User;
import com.auth.service.impl.AuthServiceImpl;

import java.util.Scanner;

public class ForgotPasswordController {
    private String newPassword;
    private String confirmPassword;
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
        if (user.isBlocked()) {
            System.out.println("Account is now locked due to 3 invalid attempts.");
            return;
        }
        try {
            otpController.sendOTP(usernameOrEmail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean otpVerified = false;
        for (int i = 1; i <= 3; i++) { // Allow 3 OTP attempts
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

            passwordController.resetPassword(usernameOrEmail, newPassword, confirmPassword);
    }
}