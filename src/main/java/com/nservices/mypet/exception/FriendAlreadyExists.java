package com.nservices.mypet.exception;

public class FriendAlreadyExists extends RuntimeException {
    public FriendAlreadyExists(String message) {
        super(message);
    }
}
