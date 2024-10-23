package coupon.dto;

import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        Long id,
        CouponResponse couponResponse,
        Long memberId,
        boolean used,
        LocalDate issueDate,
        LocalDate expirationDate
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, CouponResponse couponResponse) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                couponResponse,
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssueDate(),
                memberCoupon.getExpirationDate()
        );
    }
}
