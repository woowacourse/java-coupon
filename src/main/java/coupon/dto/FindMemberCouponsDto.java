package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record FindMemberCouponsDto(

        Long id,
        Member member,
        Coupon coupon,
        LocalDateTime issuedAt,
        LocalDateTime useEndedAt,
        boolean used,
        LocalDateTime usedAt
) {

    public FindMemberCouponsDto(MemberCoupon memberCoupon, Coupon coupon) {
        this(
                memberCoupon.getId(),
                memberCoupon.getMember(),
                coupon,
                memberCoupon.getIssuedAt(),
                memberCoupon.getUseEndedAt(),
                memberCoupon.isUsed(),
                memberCoupon.getUsedAt()
        );
    }
}
