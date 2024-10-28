package coupon.service.coupon.dto;

import coupon.domain.MinimumOrderAmount;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponsRequest(
        CouponName name,
        DiscountAmount discountAmount,
        MinimumOrderAmount minimumOrderAmount,
        Category category,
        boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {

    public static MemberCouponsRequest of(
            Coupon coupon,
            MemberCoupon memberCoupon
    ) {
        return new MemberCouponsRequest(
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
