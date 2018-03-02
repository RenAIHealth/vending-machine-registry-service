package com.stardust.machine.registry.components;


import com.stardust.machine.registry.dao.CouponRepository;
import com.stardust.machine.registry.exceptions.InvalidCouponException;
import com.stardust.machine.registry.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class VendingMachineReceiptBuilder implements ReceiptBuilder {
    private CouponRepository couponRepository;

    @Autowired
    public VendingMachineReceiptBuilder(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Receipt createFromOrder(Order order) {
        VendorMachineOrder vmOrder = (VendorMachineOrder) order;
        List<ReceiptDetail> details = new ArrayList();
        Receipt receipt = new Receipt();
        receipt.setDate(vmOrder.getOrderDate());
        receipt.setPaidAmount(vmOrder.getPaidAmount());
        receipt.setTransactionType(order.getTransactionType());
        receipt.setPaymentType(order.getPaymentType());
        receipt.setExternalTradeNumber(vmOrder.getExternalTradeNumber());
        receipt.setOperator(vmOrder.getMachineSN());
        receipt.setOrderNumber(order.getOrderNumber());

        ReceiptDetail productDetail = new ReceiptDetail();
        productDetail.setAmount(vmOrder.getProduct().getPrice());
        productDetail.setItemName(vmOrder.getProduct().getName());
        productDetail.setReceipt(receipt);
        details.add(productDetail);
        receipt.setTotalAmount(productDetail.getAmount());

        if (vmOrder.getCouponCode() != null) {
            Coupon coupon = couponRepository.getCouponByCode(vmOrder.getCouponCode());
            if (coupon == null
                    || !coupon.getStatus().equals(Coupon.CouponStatus.ACTIVATED)
                    || coupon.getActivity().getExpireDate().compareTo(new Date()) <= 0) {
                throw new InvalidCouponException();
            }

            ReceiptDetail couponDetail = new ReceiptDetail();
            couponDetail.setItemName(coupon.getCode());
            couponDetail.setAmount(-coupon.getDiscountAmount());
            details.add(couponDetail);
            receipt.setTotalAmount(receipt.getTotalAmount() + couponDetail.getAmount());
        }

        receipt.setDetails(details);
        return receipt;
    }
}
