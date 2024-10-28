package coupon.coupons.domain;

import java.util.List;
import coupon.exception.CouponException;
import coupon.membercoupon.domain.MemberCoupon;

public record Coupons(List<Coupon> coupons) {

    public Coupon findCouponBy(MemberCoupon memberCoupon) {
        return coupons.stream()
                .filter(coupon -> coupon.getId().equals(memberCoupon.getCouponId()))
                .findFirst()
                .orElseThrow(() -> new CouponException("해당 MemberCoupon에 해당하는 Coupon이 존재하지 않습니다."));
    }
}
