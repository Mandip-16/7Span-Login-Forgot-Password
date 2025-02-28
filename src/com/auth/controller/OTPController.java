package com.auth.controller;

import com.auth.exception.InvalidPasswordException;
import com.auth.exception.TooManyFailedAttemptsException;
import com.auth.service.impl.OTPServiceImpl;

public class OTPController {
    private OTPServiceImpl otpService = new OTPServiceImpl();

    // Handle OTP Sending
    public void sendOTP(String usernameOrEmail) {
        otpService.sendOTP(usernameOrEmail);
    }

    // Handle OTP Validation request
    public void verifyOTP(String usernameOrEmail, String otp) {
        try {
            otpService.verifyOTP(usernameOrEmail, otp);
        } catch (TooManyFailedAttemptsException e) {
            throw new TooManyFailedAttemptsException(e.getMessage());
        } catch (InvalidPasswordException e){
            throw new InvalidPasswordException(e.getMessage());
        }
    }
}