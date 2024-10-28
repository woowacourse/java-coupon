package coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countMemberCouponByMemberIdAndCouponId(Long memberId, Long couponId);
}
