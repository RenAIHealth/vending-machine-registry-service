package com.stardust.machine.registry.services;

import com.stardust.machine.registry.dao.CouponRepository;
import com.stardust.machine.registry.exceptions.DuplicatedKeyException;
import com.stardust.machine.registry.exceptions.InvalidCouponException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class O2OCouponService implements CouponService {
    private CouponRepository couponRepository;

    @Autowired
    public O2OCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon findAvailableCoupon(String couponCode) {
        Coupon coupon = couponRepository.getCouponByCode(couponCode);
        if (coupon == null) {
            throw new RecordNotFoundException();
        }
        if (!coupon.getStatus().equals(Coupon.CouponStatus.ACTIVATED)
                || coupon.getActivity().getExpireDate().compareTo(new Date()) <= 0) {
            throw new InvalidCouponException();
        }
        return coupon;
    }

    @Override
    public void addCoupon(Coupon coupon) {
        Coupon exists = couponRepository.getCouponByCode(coupon.getCode());
        if (exists != null) {
            throw new DuplicatedKeyException("兑换券码:" + coupon.getCode() + "已存在");
        }
        couponRepository.save(coupon);
    }
}
