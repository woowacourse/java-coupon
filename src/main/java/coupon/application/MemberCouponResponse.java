package coupon.application;

import java.time.LocalDateTime;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;

public record MemberCouponResponse(
        Long id,
        Member member,
        Boolean isUsed,
        LocalDateTime createdAt,
        Coupon coupon
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getMember(),
                memberCoupon.getIsUsed(),
                memberCoupon.getCreatedAt(),
                coupon
        );
    }
}
