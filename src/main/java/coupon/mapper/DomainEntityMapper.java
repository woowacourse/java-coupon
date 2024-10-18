package coupon.mapper;

import coupon.domain.coupon.Coupon;
import coupon.domain.user.User;
import coupon.domain.usercoupon.UserCoupon;
import coupon.entity.CouponEntity;
import coupon.entity.UserCouponEntity;
import coupon.entity.UserEntity;

public class DomainEntityMapper {

    public static CouponEntity mapToCouponEntity(Coupon coupon) {
        return new CouponEntity(
                coupon.getId(),
                coupon.getName().getName(),
                coupon.getDiscountAmount().getDiscountAmount(),
                coupon.getMinOrderAmount().getMinOrderAmount(),
                coupon.getDiscountRange().getDiscountRange(),
                coupon.getCategory(),
                coupon.getIssueDuration().getStartDateTime(),
                coupon.getIssueDuration().getEndDateTime());
    }
}
