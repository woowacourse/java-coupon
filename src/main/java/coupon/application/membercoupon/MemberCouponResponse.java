package coupon.application.membercoupon;

import java.time.LocalDateTime;
import coupon.application.coupon.CouponResponse;
import coupon.domain.coupon.Coupon;
import coupon.domain.membercoupon.MemberCoupon;

public record MemberCouponResponse(
        Long id,
        CouponResponse coupon,
        boolean isActive,
        LocalDateTime issueDate,
        LocalDateTime expireDate
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                CouponResponse.from(coupon),
                memberCoupon.isActive(),
                memberCoupon.getUsagePeriod().getIssueDate(),
                memberCoupon.getUsagePeriod().getExpireDate()
        );
    }
}
