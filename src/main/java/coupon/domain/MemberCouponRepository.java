package coupon.domain;

import java.util.List;

public interface MemberCouponRepository {

    MemberCoupon save(MemberCoupon memberCoupon);

    List<MemberCoupon> findAllByMemberId(long memberId);

    int countByMemberIdAndCouponId(long memberId, long couponId);
}
