package coupon.service.coupon.dto;

import coupon.domain.coupon.MinimumOrderAmount;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponsResponse(
        CouponName couponName,
        DiscountAmount discountAmount,
        MinimumOrderAmount minimumOrderAmount,
        Category category,
        boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {

    public static MemberCouponsResponse of(
            Coupon coupon,
            MemberCoupon memberCoupon
    ) {
        return new MemberCouponsResponse(
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getCategory(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt()
        );
    }
}
