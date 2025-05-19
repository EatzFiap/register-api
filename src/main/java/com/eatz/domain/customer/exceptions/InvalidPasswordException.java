package com.eatz.domain.customer.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Password is invalid.");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
