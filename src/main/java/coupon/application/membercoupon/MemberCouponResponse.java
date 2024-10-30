package coupon.application.membercoupon;

import coupon.application.coupon.CouponResponse;
import coupon.application.memer.MemberResponse;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import java.time.format.DateTimeFormatter;

public record MemberCouponResponse(
        Long id,
        CouponResponse couponResponse,
        MemberResponse memberResponse,
        boolean use,
        String startDate,
        String endDate
) {
    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        Coupon coupon = memberCoupon.getCoupon();
        Member member = memberCoupon.getMember();
        return new MemberCouponResponse(
                memberCoupon.getId(),
                CouponResponse.from(coupon),
                MemberResponse.from(member),
                memberCoupon.isUse(),
                memberCoupon.getStartDate().format(DateTimeFormatter.ISO_DATE),
                memberCoupon.getEndDate().format(DateTimeFormatter.ISO_DATE)
        );
    }
}
