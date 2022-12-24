package com.nservices.mypet.exception;

public class OnlyUserCanChangeStateException extends RuntimeException {
    public OnlyUserCanChangeStateException(String message) {
        super(message);
    }
}
