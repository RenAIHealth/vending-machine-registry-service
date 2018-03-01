package com.stardust.machine.registry.dao;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class CouponRepositoryTest {

    @Autowired
    private CouponRepository subject;

    private Activity activity;

    private Product product;

    private static Coupon couponA;

    private static Coupon couponB;

    private static Coupon couponC;

    @Before
    public void setup() {
        activity = new Activity();
        activity.setName("activity");
        activity.setAuid("auid");
        activity.setExpireDate(new Date());

        product = new Product();
        product.setName("product");
        product.setPrice(20.0);

        if (couponA == null) {
            couponA = new Coupon();
            couponA.setCode("couponA");
            couponA.setDiscountAmount(10.0);
            couponA.setProduct(product);
            couponA.setActivity(activity);
            subject.save(couponA);
        }

        if (couponB == null) {
            couponB = new Coupon();
            couponB.setCode("couponB");
            couponB.setDiscountAmount(10.0);
            couponB.setProduct(product);
            couponB.setStatus(Coupon.CouponStatus.OUT_OF_USAGE);
            couponB.setActivity(activity);
            subject.save(couponB);
        }

        if (couponC == null) {
            couponC = new Coupon();
            couponC.setCode("couponC");
            couponC.setDiscountAmount(10.0);
            couponC.setProduct(product);
            couponC.setActivity(activity);
            couponC.setStatus(Coupon.CouponStatus.OUT_OF_USAGE);
            subject.save(couponC);
        }
    }

    @Test
    public void couponRepositoryShouldFindCouponByCode() {
        Coupon exists = subject.getCouponByCode("couponA");
        Assert.assertNotNull(exists);
        Assert.assertEquals("couponA", exists.getCode());
        Assert.assertEquals("activity", exists.getActivity().getName());
        Assert.assertEquals("product", exists.getProduct().getName());
    }

    @Test
    public void couponRepositoryShouldCouponCouponByStatusAndActivity() {
        Long count = subject.countByActivityAuidAndStatus(activity.getAuid(), Coupon.CouponStatus.OUT_OF_USAGE);
        Assert.assertEquals(new Long(2), count);
    }
}
