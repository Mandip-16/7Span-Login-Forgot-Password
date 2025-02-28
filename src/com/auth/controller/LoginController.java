package com.auth.controller;

import com.auth.exception.UserNotFoundException;
import com.auth.model.User;
import com.auth.service.impl.AuthServiceImpl;

import java.util.Scanner;

public class LoginController {
    private AuthController authController;

    public LoginController(AuthController authController) {
        this.authController = authController;
    }

    // Handles the login flow
    public void loginFlow(Scanner scanner) {
        System.out.println("\n--- Login ---");
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

        // If account is already block
        if (user.isBlocked()) {
            System.out.println("Your account is blocked.");
            return;
        }

        // Provide 3 login attempts. If login attempts are >= 3 than account is blocked
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            try {
                authController.login(usernameOrEmail, password);
                return;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}