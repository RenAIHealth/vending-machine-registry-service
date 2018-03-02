package com.stardust.machine.registry.components;


import com.stardust.machine.registry.MachineRegistryServiceApplication;
import com.stardust.machine.registry.dao.CouponRepository;
import com.stardust.machine.registry.exceptions.InvalidCouponException;
import com.stardust.machine.registry.models.*;
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
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MachineRegistryServiceApplication.class)
@WebAppConfiguration
public class VendingMachineReceiptBuilderTest {

    private VendingMachineReceiptBuilder subject;

    private CouponRepository couponRepository;

    private VendorMachineOrder order;

    private Date orderDate;

    @Before
    public void setup() throws ParseException {
        Product product = new Product();
        product.setPrice(2.0);
        product.setName("product");

        orderDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01");

        couponRepository = EasyMock.createMock(CouponRepository.class);
        subject = new VendingMachineReceiptBuilder(couponRepository);
        order = new VendorMachineOrder();
        order.setOrderDate(orderDate);
        order.setExternalTradeNumber("external_trade_no");
        order.setOrderNumber("order_no");
        order.setAuid("auid");
        order.setPaymentType(PaymentType.ALI_QRCODE);
        order.setTransactionType(TransactionType.VENDER_MACHINE_SALE);
        order.setMachineSN("machineSN");
        order.setProduct(product);
    }

    @Test
    public void vendingMachineReceiptBuilderShouldCreateReceiptIfNoCouponApplied() {
        order.setCouponCode(null);
        order.setPaidAmount(2.0);
        Receipt receipt = subject.createFromOrder(order);
        Assert.assertEquals(orderDate, receipt.getDate());
        Assert.assertEquals("order_no", receipt.getOrderNumber());
        Assert.assertEquals("machineSN", receipt.getOperator());
        Assert.assertEquals("external_trade_no", receipt.getExternalTradeNumber());
        Assert.assertEquals(PaymentType.ALI_QRCODE, receipt.getPaymentType());
        Assert.assertEquals(TransactionType.VENDER_MACHINE_SALE, receipt.getTransactionType());
        Assert.assertEquals(1, receipt.getDetails().size());
        Assert.assertEquals("product", receipt.getDetails().get(0).getItemName());
        Assert.assertEquals(new Double(2.0), receipt.getDetails().get(0).getAmount());
        Assert.assertEquals(new Double(2.0), receipt.getTotalAmount());
        Assert.assertEquals(new Double(2.0), receipt.getPaidAmount());
    }

    @Test
    public void vendingMachineReceiptBuilderShouldCreateReceiptIfCouponApplied() throws ParseException {
        order.setCouponCode("coupon");
        order.setPaidAmount(1.0);
        Activity activity = new Activity();
        activity.setExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2098-01-01"));

        Coupon coupon = new Coupon();
        coupon.setCode("coupon");
        coupon.setDiscountAmount(1.0);
        coupon.setStatus(Coupon.CouponStatus.ACTIVATED);
        coupon.setActivity(activity);

        EasyMock.expect(couponRepository.getCouponByCode("coupon")).andReturn(coupon);

        EasyMock.replay(couponRepository);

        Receipt receipt = subject.createFromOrder(order);
        Assert.assertEquals(orderDate, receipt.getDate());
        Assert.assertEquals("order_no", receipt.getOrderNumber());
        Assert.assertEquals("machineSN", receipt.getOperator());
        Assert.assertEquals("external_trade_no", receipt.getExternalTradeNumber());
        Assert.assertEquals(PaymentType.ALI_QRCODE, receipt.getPaymentType());
        Assert.assertEquals(TransactionType.VENDER_MACHINE_SALE, receipt.getTransactionType());
        Assert.assertEquals(2, receipt.getDetails().size());
        Assert.assertEquals("product", receipt.getDetails().get(0).getItemName());
        Assert.assertEquals(new Double(2.0), receipt.getDetails().get(0).getAmount());
        Assert.assertEquals("coupon", receipt.getDetails().get(1).getItemName());
        Assert.assertEquals(new Double(-1.0), receipt.getDetails().get(1).getAmount());
        Assert.assertEquals(new Double(1.0), receipt.getPaidAmount());
    }

    @Test(expected = InvalidCouponException.class)
    public void vendingMachineReceiptBuilderShouldThrowExceptionIfCouponExpired() throws ParseException {
        order.setCouponCode("coupon");
        Activity activity = new Activity();
        activity.setExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("1999-01-01"));

        Coupon coupon = new Coupon();
        coupon.setCode("coupon");
        coupon.setDiscountAmount(1.0);
        coupon.setStatus(Coupon.CouponStatus.ACTIVATED);
        coupon.setActivity(activity);

        EasyMock.expect(couponRepository.getCouponByCode("coupon")).andReturn(coupon);

        EasyMock.replay(couponRepository);

        subject.createFromOrder(order);
    }

    @Test(expected = InvalidCouponException.class)
    public void vendingMachineReceiptBuilderShouldThrowExceptionIfCouponStatusInvalid() throws ParseException {
        order.setCouponCode("coupon");

        Coupon coupon = new Coupon();
        coupon.setStatus(Coupon.CouponStatus.OUT_OF_USAGE);

        EasyMock.expect(couponRepository.getCouponByCode("coupon")).andReturn(coupon);

        EasyMock.replay(couponRepository);

        subject.createFromOrder(order);
    }

    @Test(expected = InvalidCouponException.class)
    public void vendingMachineReceiptBuilderShouldThrowExceptionIfCouponCodeInvalid() throws ParseException {
        order.setCouponCode("coupon");

        EasyMock.expect(couponRepository.getCouponByCode("coupon")).andReturn(null);

        EasyMock.replay(couponRepository);

        subject.createFromOrder(order);
    }
}
