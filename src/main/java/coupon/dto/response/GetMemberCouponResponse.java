package coupon.dto.response;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.MemberCoupon;
import java.time.LocalDateTime;

public record GetMemberCouponResponse(
        long memberCouponId,
        long couponId,
        String couponName,
        String discountAmount,
        String minimumOrderAmount,
        int discountRate,
        String category,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {

    public GetMemberCouponResponse(MemberCoupon memberCoupon, Coupon coupon) {
        this(
                memberCoupon.getId(),
                coupon.getId(),
                coupon.getCouponName().getValue(),
                coupon.getDiscountAmount().getValue().toPlainString(),
                coupon.getMinimumOrderAmount().getValue().toPlainString(),
                coupon.getDiscountRate().getValue(),
                coupon.getCategory().name(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt()
        );
    }
}
