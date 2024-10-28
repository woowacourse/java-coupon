package coupon.dto.response;

import coupon.domain.coupon.Coupon;
import coupon.domain.membercoupon.MemberCoupon;
import java.time.LocalDateTime;

public record FindMemberCouponResponse(
        long memberCouponId,
        long couponId,
        long memberId,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt,

        String name,
        int discountAmount,
        int minOrderAmount,
        String category,
        LocalDateTime startAt,
        LocalDateTime endAt
) {

    public static FindMemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new FindMemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt(),

                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinOrderAmount().getValue(),
                coupon.getCategory().name(),
                coupon.getIssuancePeriod().getStartAt(),
                coupon.getIssuancePeriod().getEndAt()
        );
    }
}
