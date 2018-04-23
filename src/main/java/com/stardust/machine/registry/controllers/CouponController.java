package com.stardust.machine.registry.controllers;

import com.stardust.machine.registry.models.Coupon;
import com.stardust.machine.registry.services.CouponService;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping({"/api/v101/coupons"})
@EnableAutoConfiguration
public class CouponController {
    @Autowired
    private CouponService service;

    @RequestMapping(value = "/o2o/available/{code}", method={RequestMethod.GET})
    public Object getAvailableCoupon(@PathVariable String code) {
        return service.findAvailableCoupon(code);
    }

    @RequestMapping(value = "/o2o/available/{code}/qrcode", method={RequestMethod.GET})
    public Object getQRCode(@PathVariable String code) {
        ByteArrayOutputStream out = QRCode.from(code
                .toString()).to(ImageType.PNG).withSize(300, 300).stream();
        return "data:image/png;base64," + new BASE64Encoder().encode(out.toByteArray());
    }

    @RequestMapping(value = "/o2o", method={RequestMethod.POST})
    public Object registerCoupon(@RequestBody Coupon coupon) {
        service.addCoupon(coupon);
        return coupon;
    }
}
