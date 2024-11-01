package coupon.dto;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;

public record MemberCouponResponse(
        long id,
        boolean isUsed,
        String issuedAt,
        String expiredAt,
        String couponName,
        long discountPrice,
        long saleOrderPrice,
        long discountRatio,
        String category,
        String issueAvailableStartAt,
        String issuedAvailableEndAt
) {

    public MemberCouponResponse(MemberCoupon membercoupon, Coupon coupon) {
        this(
                membercoupon.getId(),
                membercoupon.isUsed(),
                membercoupon.getIssuedAt().toString(),
                membercoupon.getExpiredAt().toString(),
                coupon.getName().getValue(),
                coupon.getDiscountPrice().getPrice(),
                coupon.getSaleOrderPrice().getPrice(),
                coupon.getDiscountRatio().getRatio(),
                coupon.getCategory().name(),
                coupon.getDuration().getStartAt().toString(),
                coupon.getDuration().getEndAt().toString()
        );
    }
}
