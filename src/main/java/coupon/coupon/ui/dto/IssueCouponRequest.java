package coupon.coupon.ui.dto;

public record IssueCouponRequest(Long couponId, Long memberId) {
    public IssueCouponRequest {
        if (couponId == null || memberId == null) {
            throw new IllegalArgumentException("couponId and memberId must not be null");
        }
    }
}
