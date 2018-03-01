package com.stardust.machine.registry.controllers;

import com.stardust.machine.registry.models.Coupon;
import com.stardust.machine.registry.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"v1-0-1/api/coupons"})
@EnableAutoConfiguration
public class CouponController {
    @Autowired
    private CouponService service;

    @RequestMapping(value = "/o2o/available/{code}", method={RequestMethod.GET})
    public Object getAvailableCoupon(@PathVariable String code) {
        return service.findAvailableCoupon(code);
    }

    @RequestMapping(value = "/o2o", method={RequestMethod.POST})
    public Object registerCoupon(@RequestBody Coupon coupon) {
        service.addCoupon(coupon);
        return coupon;
    }
}
