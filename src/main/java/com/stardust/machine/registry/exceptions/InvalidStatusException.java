package com.stardust.machine.registry.exceptions;


public class InvalidStatusException extends ServiceFatalException {
    @Override
    public String getMessage() {
        return "Can not operate this status of package";
    }
}
