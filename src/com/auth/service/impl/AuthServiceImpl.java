package com.auth.service.impl;

import com.auth.exception.InvalidPasswordException;
import com.auth.exception.TooManyFailedAttemptsException;
import com.auth.exception.UserNotFoundException;
import com.auth.model.User;
import com.auth.service.AuthService;

import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private static final Map<String, User> userRepository = new HashMap<>();

    //User List for demo
    static {
        userRepository.put("Mandip", new User("Mandip", "mandip.patel@example.com", "Password_123"));
    }

    // Find user by username or email
    public User findUser(String usernameOrEmail) {
        return userRepository.values().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(usernameOrEmail) || user.getEmail().equalsIgnoreCase(usernameOrEmail))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found: " + usernameOrEmail + ". Please try again."));
    }

    // Login functionality
    @Override
    public void login(String usernameOrEmail, String password) {
        User user = findUser(usernameOrEmail);

        // Password validation
        if (!user.getPassword().equals(password)) {
            user.setLoginAttempts(user.getLoginAttempts() + 1);

            // Account is blocked due to 3 or more wrong login attempts
            if (user.getLoginAttempts() >= 3) {
                user.setBlocked(true); // Block account
                throw new TooManyFailedAttemptsException("The account is blocked after 3 invalid login attempts. Please reset your password.");
            }

            throw new InvalidPasswordException("Invalid password. Attempts remaining: " + (3 - user.getLoginAttempts()));
        }

        // Successful login, Reset login attempts
        user.setLoginAttempts(0);
        System.out.println("Login successful for user: " + usernameOrEmail);
    }
}