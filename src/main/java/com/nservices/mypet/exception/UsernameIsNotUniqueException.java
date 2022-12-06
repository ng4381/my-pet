package com.nservices.mypet.exception;

public class UsernameIsNotUniqueException extends RuntimeException{
    public UsernameIsNotUniqueException(String message) {
        super(message);
    }
}
