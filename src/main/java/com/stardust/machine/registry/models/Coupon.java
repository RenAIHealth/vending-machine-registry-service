package com.stardust.machine.registry.models;

import javax.persistence.*;

@Entity
@Table(name = "coupons")
@AttributeOverrides({
        @AttributeOverride(name = "product.name", column = @Column(name = "product_name")),
        @AttributeOverride(name = "product.price", column = @Column(name = "product_price")),
        @AttributeOverride(name = "activity.name", column = @Column(name = "activity_name")),
        @AttributeOverride(name = "activity.expireDate", column = @Column(name = "expire_date"))
})
public class Coupon extends DataModel {
    public enum CouponStatus {
        ACTIVATED,
        INACTIVE,
        OUT_OF_USAGE
    }

    private String code;

    @Embedded
    private Product product;

    @Embedded
    private Activity activity;

    private Double discountAmount;

    private CouponStatus status = CouponStatus.ACTIVATED;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(unique = true, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "discount_amount")
    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
