package coupon.service.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponDto(
        Long id,
        Long memberId,
        boolean used,
        LocalDateTime issuedAt,
        Coupon coupon
) {

    public static MemberCouponDto from(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponDto(
                memberCoupon.getId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                coupon
        );
    }
}
