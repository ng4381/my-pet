package com.nservices.mypet.exception;

public class UserAlreadyHasAnOwnerException extends RuntimeException {
    public UserAlreadyHasAnOwnerException(String message) {
        super(message);
    }
}
