package coupon.member.request;

public record MemberCouponRequest(
        Long memberId,
        Long couponId
) {
}
