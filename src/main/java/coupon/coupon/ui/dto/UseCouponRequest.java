package coupon.coupon.ui.dto;

public record UseCouponRequest(Long memberId, Long memberCouponId) {
    public UseCouponRequest {
        if (memberId == null || memberCouponId == null) {
            throw new IllegalArgumentException("couponId and memberId must not be null");
        }
    }
}
