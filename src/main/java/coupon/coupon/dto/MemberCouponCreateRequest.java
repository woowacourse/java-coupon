package coupon.coupon.dto;

public record MemberCouponCreateRequest(
        Long memberId,
        Long couponId
) {
}
