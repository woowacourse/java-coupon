package coupon.Dto;

import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record CouponOfMemberResponse(
        Long id,
        boolean isUsed,
        LocalDate issueDate,
        LocalDate expirationDate,
        CouponResponse couponResponse
) {

    public CouponOfMemberResponse(MemberCoupon memberCoupon, CouponResponse couponResponse) {
        this(
                memberCoupon.getId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssueDate(),
                memberCoupon.getExpirationDate(),
                couponResponse
        );
    }
}
