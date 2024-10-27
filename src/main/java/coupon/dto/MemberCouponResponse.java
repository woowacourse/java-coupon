package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long id, String couponName, Integer discount, Integer minOrderPrice,
                                   String category, boolean used, LocalDateTime expiredAt) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                coupon.getName(),
                coupon.getDiscount(),
                coupon.getMinOrderPrice(),
                coupon.getCategory().name(),
                memberCoupon.isUsed(),
                memberCoupon.getExpiredAt()
        );
    }
}
