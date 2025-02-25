package com.auth.service.impl;

import com.auth.exception.InvalidPasswordException;
import com.auth.exception.TooManyFailedAttemptsException;
import com.auth.model.User;
import com.auth.service.OTPService;
import com.auth.util.InputUtil;

import java.util.HashMap;
import java.util.Map;

public class OTPServiceImpl implements OTPService {
    private AuthServiceImpl authService = new AuthServiceImpl();
    private Map<String, String> otpStore = new HashMap<>();

    @Override
    public void sendOTP(String usernameOrEmail) {
        User user = authService.findUser(usernameOrEmail);
        String otp = InputUtil.generateOTP();
        otpStore.put(usernameOrEmail, otp);

        System.out.println("OTP sent to user: " + usernameOrEmail + ", OTP: " + otp); // Simulate OTP sending
    }

    @Override
    public void verifyOTP(String usernameOrEmail, String otp) {
        User user = authService.findUser(usernameOrEmail);

        if (user.getOtpAttempts() >= 3) {
            throw new TooManyFailedAttemptsException("Too many failed attempts. Account is locked.");
        }

        if (!otp.equals(otpStore.get(usernameOrEmail))) {
            user.setOtpAttempts(user.getOtpAttempts() + 1);
            throw new InvalidPasswordException("Invalid OTP.");
        }

        user.setOtpAttempts(0); // Reset OTP attempts on success
        System.out.println("OTP verified successfully for user: " + usernameOrEmail);
    }
}