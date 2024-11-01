package coupon.dto;

import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        Long id,
        String name,
        boolean used,
        int discountAmount,
        int minimumOrderAmount,
        String category,
        LocalDate issuedAt,
        LocalDate expiredAt
) {

    public static MemberCouponResponse from(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                coupon.getName(),
                memberCoupon.isUsed(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getCategory().name(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt()
        );
    }
}
