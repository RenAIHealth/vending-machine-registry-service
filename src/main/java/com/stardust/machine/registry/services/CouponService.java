package com.stardust.machine.registry.services;

import com.stardust.machine.registry.models.Coupon;

public interface CouponService {
    Coupon findAvailableCoupon(String couponCode);

    void addCoupon(Coupon coupon);
}
