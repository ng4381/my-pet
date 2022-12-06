package com.nservices.mypet.exception;

public class UserAlreadyHasAPetException extends RuntimeException {
    public UserAlreadyHasAPetException(String message) {
        super(message);
    }
}
