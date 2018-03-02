package com.stardust.machine.registry.exceptions;


public class MachineSNConflictException extends ServiceFatalException {
    @Override
    public String getMessage() {
        return "贩卖机编号与订单不符";
    }
}
