package com.pragma.powerup.domain.exception;

public class LogNoFoundException extends RuntimeException {
    public LogNoFoundException() {
        super("The order hasn't traceability");
    }
}