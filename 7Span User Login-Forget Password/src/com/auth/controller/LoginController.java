package com.auth.controller;

import java.util.Scanner;

public class LoginController {
    private AuthController authController;

    public LoginController(AuthController authController) {
        this.authController = authController;
    }

    // Handles the login flow
    public void loginFlow(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username or email: ");
        String usernameOrEmail = scanner.nextLine();

        for (int i = 0; i < 3; i++) { // Allow 3 login attempts
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            try {
                authController.login(usernameOrEmail, password);
                return; // Exit if login is successful
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                if (i == 2) {
                    System.out.println("Account is now locked due to 3 invalid attempts. Please use Forgot Password to reset your account.");
                }
            }
        }
    }
}