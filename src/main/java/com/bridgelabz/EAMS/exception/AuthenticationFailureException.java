package com.bridgelabz.EAMS.exception;

public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException() {
        super("Authentication failed: Incorrect credentials.");
    }
}