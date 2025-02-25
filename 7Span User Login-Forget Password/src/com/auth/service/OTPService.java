package com.auth.service;

public interface OTPService {
    void sendOTP(String usernameOrEmail);
    void verifyOTP(String usernameOrEmail, String otp);
}