package com.stardust.machine.registry.exceptions;


public class InvalidTokenException extends ServiceFatalException {
    private String message;

    public InvalidTokenException(String token, String machineSN) {
        message = "Machine SN '" + machineSN
                + "' doesn't match token '" + token + "'";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
