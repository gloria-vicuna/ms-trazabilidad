package com.pragma.powerup.domain.exception;

public class OnlyEmployeeCanUpdateLogException extends RuntimeException {
    public OnlyEmployeeCanUpdateLogException() {
        super("Only an employee can update the order");
    }
}
