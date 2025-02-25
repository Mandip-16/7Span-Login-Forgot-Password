package com.auth.controller;

import com.auth.service.impl.OTPServiceImpl;

public class OTPController {
    private OTPServiceImpl otpService = new OTPServiceImpl();

    public void sendOTP(String usernameOrEmail) {
        otpService.sendOTP(usernameOrEmail);
    }

    public void verifyOTP(String usernameOrEmail, String otp) {
        try {
            otpService.verifyOTP(usernameOrEmail, otp);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}