package com.auth;

import com.auth.controller.AuthController;
import com.auth.controller.OTPController;
import com.auth.controller.PasswordController;
import com.auth.controller.LoginController;
import com.auth.controller.ForgotPasswordController;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instantiate controllers
        AuthController authController = new AuthController();
        OTPController otpController = new OTPController();
        PasswordController passwordController = new PasswordController();
        LoginController loginController = new LoginController(authController);
        ForgotPasswordController forgotPasswordController = new ForgotPasswordController(otpController, passwordController);

        System.out.println("Welcome to the User Login System");
        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Login");
            System.out.println("2. Forgot Password");
            System.out.println("3. Exit");
            System.out.println("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> loginController.loginFlow(scanner);
                case 2 -> forgotPasswordController.forgotPasswordFlow(scanner);
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}