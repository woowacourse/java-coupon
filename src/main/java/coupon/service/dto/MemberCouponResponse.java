package coupon.service.dto;

import java.time.LocalDateTime;

public class MemberCouponResponse {

    private final long couponId;
    private final String couponName;
    private final int discountAmount;
    private final int minimOrderAmount;
    private final String couponCategory;
    private final boolean used;
    private final LocalDateTime issueStartedAt;
    private final LocalDateTime issueEndedAt;
    private final LocalDateTime issuedAt;
    private final LocalDateTime useEndedAt;

    public MemberCouponResponse(
            long couponId,
            String couponName,
            int discountAmount,
            int minimOrderAmount,
            String couponCategory,
            boolean used,
            LocalDateTime issueStartedAt,
            LocalDateTime issueEndedAt,
            LocalDateTime issuedAt,
            LocalDateTime useEndedAt
    ) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.minimOrderAmount = minimOrderAmount;
        this.couponCategory = couponCategory;
        this.used = used;
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
        this.issuedAt = issuedAt;
        this.useEndedAt = useEndedAt;
    }
}
