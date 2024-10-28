package coupon.mapper;

import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;

public class EntityDomainMapper {

    public static Coupon mapToCoupon(CouponEntity couponEntity) {
        return new Coupon(
                couponEntity.getId(),
                couponEntity.getName(),
                couponEntity.getDiscountAmount(),
                couponEntity.getMinOrderAmount(),
                couponEntity.getCategory(),
                couponEntity.getIssueEndDate(),
                couponEntity.getIssueEndDate());
    }
}
