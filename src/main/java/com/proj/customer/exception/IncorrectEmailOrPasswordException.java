package com.proj.customer.exception;

public class IncorrectEmailOrPasswordException extends RuntimeException {
    public IncorrectEmailOrPasswordException() {
        super("Email or password is incorrect");
    }

    public IncorrectEmailOrPasswordException(String message) {
        super(message);
    }
}
