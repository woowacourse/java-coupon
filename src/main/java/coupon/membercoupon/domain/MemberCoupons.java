package coupon.membercoupon.domain;

import java.util.ArrayList;
import java.util.List;
import coupon.coupons.domain.Coupons;
import coupon.exception.CouponException;


public record MemberCoupons(List<MemberCoupon> memberCoupons) {

    private static final int MAX_ISSUED_COUPON_SIZE = 5;

    public void validateCouponIssuanceLimit() {
        if (memberCoupons.size() >= MAX_ISSUED_COUPON_SIZE) {
            throw new CouponException(String.format("최대로 발급 받을 수 있는 쿠폰 수는 %s개 입니다.", MAX_ISSUED_COUPON_SIZE));
        }
    }

    public List<Long> getCouponsIds() {
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();
    }

    public List<MemberCouponDetail> getMemberCouponDetails(Coupons coupons) {
        List<MemberCouponDetail> memberCouponDetails = new ArrayList<>();
        memberCoupons.forEach(memberCoupon ->
                memberCouponDetails.add(
                        new MemberCouponDetail(memberCoupon, coupons.findCouponBy(memberCoupon)))
        );
        return memberCouponDetails;
    }
}
