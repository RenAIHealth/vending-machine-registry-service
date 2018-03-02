package com.stardust.machine.registry.models;

import java.io.Serializable;

public class VendorMachineOrder extends Order implements Serializable {

    private String couponCode;

    private String auid;

    private Product product;

    private String externalTradeNumber;

    private String machineSN;

    private Double paidAmount;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getExternalTradeNumber() {
        return externalTradeNumber;
    }

    public void setExternalTradeNumber(String externalTradeNo) {
        this.externalTradeNumber = externalTradeNo;
    }

    public String getMachineSN() {
        return machineSN;
    }

    public void setMachineSN(String machineSN) {
        this.machineSN = machineSN;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }
}
