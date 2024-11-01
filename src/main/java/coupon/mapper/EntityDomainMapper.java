package coupon.mapper;

import coupon.domain.coupon.Coupon;
import coupon.domain.usercoupon.UserCoupon;
import coupon.entity.CouponEntity;
import coupon.entity.UserCouponEntity;

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

    public static UserCoupon mapToUserCoupon(UserCouponEntity userCouponEntity) {
        return new UserCoupon(
                userCouponEntity.getId(),
                userCouponEntity.getUserId(),
                userCouponEntity.getCouponId(),
                userCouponEntity.isUsed(),
                userCouponEntity.getExpiredDateTime());
    }
}
