package coupon.coupon.dto;

public record MemberCouponRequest(
        long couponId,
        long memberId
) {
}
