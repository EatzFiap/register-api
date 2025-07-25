package com.eatz.shared.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid credentials. Check your email and password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
