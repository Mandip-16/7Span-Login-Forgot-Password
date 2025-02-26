package com.auth.util;

import java.util.Random;

public class InputUtil {
    public static String generateOTP() {
        return String.valueOf(1000 + new Random().nextInt(9000)); // Generate a 4-digit OTP
    }

    public static boolean isValidPassword(String password) {
        // Define the password requirements using a regular expression
        String passwordRegex = "^(?=.*[A-Z])" +   // At least one uppercase letter
                "(?=.*[a-z])" +   // At least one lowercase letter
                "(?=.*\\d)" +     // At least one digit
                "(?=.*_)" +       // At least one underscore (_)
                ".{8,}$";         // Minimum length of 8 characters

        return password.matches(passwordRegex);
    }

}