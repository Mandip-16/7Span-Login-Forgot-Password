package com.auth.controller;

import com.auth.exception.InvalidPasswordException;
import com.auth.exception.TooManyFailedAttemptsException;
import com.auth.exception.UserNotFoundException;
import com.auth.service.impl.AuthServiceImpl;

public class AuthController {
    private final AuthServiceImpl authService = new AuthServiceImpl();

    // Handles the login request
    public void login(String usernameOrEmail, String password) {
        try {
            authService.login(usernameOrEmail, password);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (TooManyFailedAttemptsException e) {
            throw new TooManyFailedAttemptsException(e.getMessage());
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordException(e.getMessage());
        }
    }
}