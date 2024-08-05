package coupon.coupon.ui.dto;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponLimitType;
import coupon.coupon.domain.CouponStatus;
import java.time.LocalDateTime;

public record CouponResponse(Long id, int discountAmount, int minimumOrderPrice, CouponStatus couponStatus,
                             boolean issuable, boolean usable, LocalDateTime issueStartedAt, LocalDateTime issueEndedAt,
                             CouponLimitType limitType, Long issueLimit, Long issueCount, Long useLimit,
                             Long useCount) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(coupon.getId(), coupon.getDiscountAmount(), coupon.getMinimumOrderPrice(),
                coupon.getCouponStatus(), coupon.isIssuable(), coupon.isUsable(), coupon.getIssueStartedAt(),
                coupon.getIssueEndedAt(), coupon.getLimitType(), coupon.getIssueLimit(), coupon.getIssueCount(),
                coupon.getUseLimit(), coupon.getUseCount());
    }
}
