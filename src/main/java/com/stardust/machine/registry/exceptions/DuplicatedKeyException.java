package com.stardust.machine.registry.exceptions;

public class DuplicatedKeyException extends ServiceFatalException {
    private String message;

    public DuplicatedKeyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
