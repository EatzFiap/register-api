package com.eatz.shared.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid credentials. Check your email and password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
