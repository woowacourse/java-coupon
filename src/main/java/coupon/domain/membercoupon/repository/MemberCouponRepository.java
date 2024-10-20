package coupon.domain.membercoupon.repository;

import coupon.domain.membercoupon.MemberCoupon;
import java.util.List;
import java.util.Optional;

public interface MemberCouponRepository {

    List<MemberCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findAllByMemberId(Long memberId);

    Optional<MemberCoupon> save(MemberCoupon memberCoupon);
}
