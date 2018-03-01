package com.stardust.machine.registry.exceptions;


import com.stardust.machine.registry.models.Coupon;

public class InvalidCouponException extends ServiceFatalException {
    private String message;

    public InvalidCouponException(Coupon coupon) {
        message = "兑换券:" + coupon.getCode() + "已经兑换过了或者所属活动已经结束";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
