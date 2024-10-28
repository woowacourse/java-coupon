package coupon.membercoupon.service.port;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;

public interface MemberCouponRepository {

    int countByMemberAndCoupon(Member member, Coupon coupon);

    void save(MemberCoupon memberCoupon);
}
