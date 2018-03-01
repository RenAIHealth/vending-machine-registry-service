package com.stardust.machine.registry.services;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.dao.CouponRepository;
import com.stardust.machine.registry.exceptions.DuplicatedKeyException;
import com.stardust.machine.registry.exceptions.InvalidCouponException;
import com.stardust.machine.registry.exceptions.RecordNotFoundException;
import com.stardust.machine.registry.models.Activity;
import com.stardust.machine.registry.models.Coupon;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class O2OCouponServiceTest {

    private O2OCouponService subject;

    private CouponRepository couponRepository;

    @Before
    public void setup() {
        couponRepository = EasyMock.createMock(CouponRepository.class);
        subject = new O2OCouponService(couponRepository);
    }

    @Test
    public void testIfO2OCouponServiceCanAddCouponWhenNoDuplication() {
        Coupon coupon = new Coupon();
        coupon.setCode("coupon_code");

        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(null).once();
        EasyMock.expect(couponRepository.save(coupon)).andReturn(coupon).once();
        EasyMock.replay(couponRepository);
        subject.addCoupon(coupon);
    }

    @Test(expected = DuplicatedKeyException.class)
    public void testIfO2OCouponServiceThrowExceptionWhenAddCouponWithDuplicatedCode() {
        Coupon coupon = new Coupon();
        coupon.setCode("coupon_code");

        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(coupon).once();
        EasyMock.replay(couponRepository);
        subject.addCoupon(coupon);
    }

    @Test
    public void testIfO2OCouponServiceCanFindAvailableCoupon() throws ParseException {
        Coupon coupon = new Coupon();
        coupon.setCode("coupon_code");
        Activity activity = new Activity();
        activity.setExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2099-12-31"));
        coupon.setActivity(activity);
        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(coupon).once();
        EasyMock.replay(couponRepository);
        Coupon available = subject.findAvailableCoupon("coupon_code");
        Assert.assertEquals("coupon_code", available.getCode());
    }

    @Test(expected = RecordNotFoundException.class)
    public void testIfO2OCouponServiceThrowExceptionWhenNoCouponFound() throws ParseException {
        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(null).once();
        EasyMock.replay(couponRepository);
        subject.findAvailableCoupon("coupon_code");
    }

    @Test(expected = InvalidCouponException.class)
    public void testIfO2OCouponServiceThrowExceptionWhenCouponStatusInvalid() throws ParseException {
        Coupon coupon = new Coupon();
        coupon.setStatus(Coupon.CouponStatus.OUT_OF_USAGE);
        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(coupon).once();
        EasyMock.replay(couponRepository);
        subject.findAvailableCoupon("coupon_code");
    }

    @Test(expected = InvalidCouponException.class)
    public void testIfO2OCouponServiceThrowExceptionWhenCouponExipred() throws ParseException {
        Coupon coupon = new Coupon();
        coupon.setCode("coupon_code");
        Activity activity = new Activity();
        activity.setExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("1999-12-31"));
        coupon.setActivity(activity);
        EasyMock.expect(couponRepository.getCouponByCode("coupon_code")).andReturn(coupon).once();
        EasyMock.replay(couponRepository);
        subject.findAvailableCoupon("coupon_code");
    }
}
