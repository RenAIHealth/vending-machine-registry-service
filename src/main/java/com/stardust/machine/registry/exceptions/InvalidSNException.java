package com.stardust.machine.registry.exceptions;


import com.stardust.machine.registry.models.SNIdentityModel;

public class InvalidSNException extends ServiceFatalException {
    private String message;

    public InvalidSNException(SNIdentityModel object) {
        if (object.getSn() == null || object.getSn().isEmpty()) {
            message = "SN can not be empty";
        } else {
            message = "Duplicated SN '" + object.getSn() + "'";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
