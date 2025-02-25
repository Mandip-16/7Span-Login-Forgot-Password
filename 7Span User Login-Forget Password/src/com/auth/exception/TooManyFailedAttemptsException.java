package com.auth.exception;

public class TooManyFailedAttemptsException extends RuntimeException{
    public TooManyFailedAttemptsException(String message) {
        super(message);
    }
}
