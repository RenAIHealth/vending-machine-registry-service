package com.stardust.machine.registry.exceptions;


public class InvalidCouponException extends ServiceFatalException {
    private String message;

    public InvalidCouponException() {
        message = "兑换券已经兑换过了或者所属活动已经结束";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
