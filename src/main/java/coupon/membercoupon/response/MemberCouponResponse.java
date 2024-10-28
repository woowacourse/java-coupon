package coupon.membercoupon.response;

import coupon.coupon.repository.CouponEntity;
import coupon.membercoupon.repository.MemberCouponEntity;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        String name,
        Boolean available,
        Long discountRate,
        Long discountMoney,
        Long orderPrice,
        LocalDateTime start,
        LocalDateTime end
) {

    public MemberCouponResponse(CouponEntity couponEntity, MemberCouponEntity memberCouponEntity) {
        this(couponEntity.getName(), memberCouponEntity.getAvailable(), couponEntity.getDiscountRate(),
                couponEntity.getDiscountMoney(), couponEntity.getOrderPrice(), memberCouponEntity.getStart(),
                memberCouponEntity.getEnd());
    }
}
