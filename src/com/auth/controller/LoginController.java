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
                if (i == 2) {
                    System.out.println("Account is now locked due to 3 invalid attempts. Please use Forgot Password to reset your account.");
                }
            }
        }
    }
}