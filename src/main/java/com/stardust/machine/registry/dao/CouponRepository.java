package com.stardust.machine.registry.dao;

import com.stardust.machine.registry.models.Coupon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends DataRepository<Coupon, Long> {
    Coupon getCouponByCode(String code);

    @Query(countQuery = "SELECT COUNT(id) FROM Coupon coupon WHERE coupon.status = :status AND coupon.activity.auid = :auid")
    Long countByActivityAuidAndStatus(@Param("auid") String auid, @Param("status") Coupon.CouponStatus status);
}