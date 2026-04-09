package com.pragma.powerup.domain.exception;

public class NotOwnerOfOrderException extends RuntimeException {
    public NotOwnerOfOrderException() {
        super("You are not the owner of this order");
    }
}
