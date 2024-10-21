package coupon.domain.membercoupon.repository;

import coupon.domain.membercoupon.MemberCoupon;
import java.util.List;

public interface MemberCouponRepository {

    List<MemberCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findAllByMemberId(Long memberId);

    MemberCoupon save(MemberCoupon memberCoupon);
}
