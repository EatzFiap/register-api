package com.eatz.shared.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Password is invalid.");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
