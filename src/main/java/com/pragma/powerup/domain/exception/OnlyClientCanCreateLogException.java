package com.pragma.powerup.domain.exception;

public class OnlyClientCanCreateLogException extends RuntimeException {
    public OnlyClientCanCreateLogException() {
        super("Only clients can create Orders.");
    }
}
