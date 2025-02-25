package com.auth.controller;

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
        System.out.print("Enter username or email: ");
        String usernameOrEmail = scanner.nextLine();

        otpController.sendOTP(usernameOrEmail);

        boolean otpVerified = false;
        for (int i = 1; i <= 3; i++) { // Allow 3 OTP attempts
            System.out.print("Enter OTP (attempt " + i + "): ");
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
            System.out.println("Too many failed OTP attempts. Account is blocked.");
        } else {
            System.out.println("\nEnter New Password:");
            System.out.print("New Password: ");
            String newPassword = scanner.nextLine();
            System.out.print("Confirm Password: ");
            String confirmPassword = scanner.nextLine();

            passwordController.resetPassword(usernameOrEmail, newPassword, confirmPassword);
        }
    }
}