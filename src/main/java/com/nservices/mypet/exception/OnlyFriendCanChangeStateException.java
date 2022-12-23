package com.nservices.mypet.exception;

public class OnlyFriendCanChangeStateException extends RuntimeException {
    public OnlyFriendCanChangeStateException(String message) {
        super(message);
    }
}
