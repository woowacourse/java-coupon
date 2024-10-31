package coupon.mapper;

import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;

public class DomainEntityMapper {

    public static CouponEntity mapToCouponEntity(Coupon coupon) {
        return new CouponEntity(
                coupon.getName().getName(),
                coupon.getDiscountAmount().getDiscountAmount(),
                coupon.getMinOrderAmount().getMinOrderAmount(),
                coupon.getDiscountRange().getDiscountRange(),
                coupon.getCategory(),
                coupon.getIssueDuration().getStartDateTime(),
                coupon.getIssueDuration().getEndDateTime());
    }
}
